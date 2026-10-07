package com.medicare.service;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medicare.dto.AppointmentResponse;
import com.medicare.dto.PaymentOrderResponse;
import com.medicare.dto.PaymentVerificationRequest;

import com.medicare.entity.Appointment;
import com.medicare.entity.AppointmentStatus;
import com.medicare.entity.PaymentStatus;
import com.medicare.entity.User;

import com.medicare.exception.AppointmentNotFoundException;
import com.medicare.exception.PaymentException;
import com.medicare.exception.UserNotFoundException;

import com.medicare.repository.AppointmentRepository;
import com.medicare.repository.UserRepository;

import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;


@Service
public class PaymentService {


    private final AppointmentRepository
            appointmentRepository;

    private final UserRepository
            userRepository;

    private final AppointmentService
            appointmentService;


    @Value("${razorpay.key-id}")
    private String keyId;


    @Value("${razorpay.key-secret}")
    private String keySecret;


    public PaymentService(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            AppointmentService appointmentService) {

        this.appointmentRepository =
                appointmentRepository;

        this.userRepository =
                userRepository;

        this.appointmentService =
                appointmentService;
    }


    // -------------------------------------------------
    // CREATE RAZORPAY ORDER
    // -------------------------------------------------

    @Transactional
    @PreAuthorize("hasRole('PATIENT')")
    public PaymentOrderResponse
    createOrder(
            Long appointmentId,
            String email) {


        User patient =
                findUserByEmail(
                        email
                );


        Appointment appointment =
                findAppointmentById(
                        appointmentId
                );


        verifyOwnership(
                appointment,
                patient
        );


        validateForPayment(
                appointment
        );


        long amount =
                convertToPaise(
                        appointment
                                .getConsultationFee()
                );


        /*
         * If an order already exists,
         * reuse it for payment retry.
         */
        if (
                appointment
                        .getRazorpayOrderId()
                        != null
                        &&
                        !appointment
                                .getRazorpayOrderId()
                                .isBlank()
        ) {

            return new PaymentOrderResponse(
                    appointment.getId(),
                    appointment
                            .getRazorpayOrderId(),
                    amount,
                    "INR",
                    keyId
            );
        }


        try {

            RazorpayClient client =
                    new RazorpayClient(
                            keyId,
                            keySecret
                    );


            JSONObject orderRequest =
                    new JSONObject();


            orderRequest.put(
                    "amount",
                    amount
            );


            orderRequest.put(
                    "currency",
                    "INR"
            );


            orderRequest.put(
                    "receipt",
                    "appt_"
                            + appointment
                            .getId()
            );


            JSONObject notes =
                    new JSONObject();


            notes.put(
                    "appointmentId",
                    appointment
                            .getId()
            );


            orderRequest.put(
                    "notes",
                    notes
            );


            Order order =
                    client.orders
                            .create(
                                    orderRequest
                            );


            String razorpayOrderId =
                    order.get(
                            "id"
                    );


            appointment
                    .setRazorpayOrderId(
                            razorpayOrderId
                    );


            appointmentRepository
                    .save(
                            appointment
                    );


            return new PaymentOrderResponse(
                    appointment.getId(),
                    razorpayOrderId,
                    amount,
                    "INR",
                    keyId
            );


        } catch (
                RazorpayException exception
        ) {

            throw new PaymentException(
                    "Unable to create payment order: "
                            + getRazorpayMessage(
                            exception
                    )
            );
        }
    }


    // -------------------------------------------------
    // VERIFY PAYMENT
    // -------------------------------------------------

    @Transactional
    @PreAuthorize("hasRole('PATIENT')")
    public AppointmentResponse
    verifyPayment(
            Long appointmentId,
            PaymentVerificationRequest request,
            String email) {


        User patient =
                findUserByEmail(
                        email
                );


        Appointment appointment =
                findAppointmentById(
                        appointmentId
                );


        verifyOwnership(
                appointment,
                patient
        );


        /*
         * Makes verification safe
         * if it is accidentally retried.
         */
        if (
                appointment
                        .getPaymentStatus()
                        ==
                        PaymentStatus.PAID
        ) {

            return appointmentService
                    .getAppointmentById(
                            appointmentId,
                            email
                    );
        }


        if (
                appointment
                        .getAppointmentStatus()
                        !=
                        AppointmentStatus.PENDING
        ) {

            throw new IllegalArgumentException(
                    "Only pending appointments can be paid"
            );
        }


        if (
                appointment
                        .getPaymentStatus()
                        !=
                        PaymentStatus.UNPAID
        ) {

            throw new IllegalArgumentException(
                    "Appointment is not available for payment"
            );
        }


        if (
                appointment
                        .getRazorpayOrderId()
                        == null
                        ||
                        appointment
                                .getRazorpayOrderId()
                                .isBlank()
        ) {

            throw new IllegalArgumentException(
                    "Payment order has not been created"
            );
        }


        try {

            // -----------------------------------------
            // 1. VERIFY RAZORPAY SIGNATURE
            // -----------------------------------------

            JSONObject attributes =
                    new JSONObject();


            /*
             * IMPORTANT:
             * Use order ID stored in database.
             * Do not trust the browser order ID.
             */
            attributes.put(
                    "razorpay_order_id",
                    appointment
                            .getRazorpayOrderId()
            );


            attributes.put(
                    "razorpay_payment_id",
                    request
                            .getRazorpayPaymentId()
            );


            attributes.put(
                    "razorpay_signature",
                    request
                            .getRazorpaySignature()
            );


            boolean validSignature =
                    Utils
                            .verifyPaymentSignature(
                                    attributes,
                                    keySecret
                            );


            if (!validSignature) {

                throw new IllegalArgumentException(
                        "Invalid payment signature"
                );
            }


            // -----------------------------------------
            // 2. FETCH PAYMENT DIRECTLY FROM RAZORPAY
            // -----------------------------------------

            RazorpayClient client =
                    new RazorpayClient(
                            keyId,
                            keySecret
                    );


            Payment payment =
                    client.payments
                            .fetch(
                                    request
                                            .getRazorpayPaymentId()
                            );


            String paymentOrderId =
                    payment.get(
                            "order_id"
                    );


            Number paymentAmount =
                    payment.get(
                            "amount"
                    );


            String paymentCurrency =
                    payment.get(
                            "currency"
                    );


            String razorpayStatus =
                    payment.get(
                            "status"
                    );


            long expectedAmount =
                    convertToPaise(
                            appointment
                                    .getConsultationFee()
                    );


            // -----------------------------------------
            // 3. VERIFY ORDER
            // -----------------------------------------

            if (
                    !appointment
                            .getRazorpayOrderId()
                            .equals(
                                    paymentOrderId
                            )
            ) {

                throw new IllegalArgumentException(
                        "Payment order does not match appointment"
                );
            }


            // -----------------------------------------
            // 4. VERIFY AMOUNT
            // -----------------------------------------

            if (
                    paymentAmount == null
                            ||
                            paymentAmount
                                    .longValue()
                                    !=
                                    expectedAmount
            ) {

                throw new IllegalArgumentException(
                        "Payment amount does not match consultation fee"
                );
            }


            // -----------------------------------------
            // 5. VERIFY CURRENCY
            // -----------------------------------------

            if (
                    !"INR"
                            .equalsIgnoreCase(
                                    paymentCurrency
                            )
            ) {

                throw new IllegalArgumentException(
                        "Invalid payment currency"
                );
            }


            // -----------------------------------------
            // 6. VERIFY PAYMENT CAPTURED
            // -----------------------------------------

            if (
                    !"captured"
                            .equalsIgnoreCase(
                                    razorpayStatus
                            )
            ) {

                throw new IllegalArgumentException(
                        "Payment has not been captured"
                );
            }


            // -----------------------------------------
            // 7. UPDATE APPOINTMENT
            // -----------------------------------------

            appointment
                    .setRazorpayPaymentId(
                            request
                                    .getRazorpayPaymentId()
                    );


            appointment
                    .setPaymentStatus(
                            PaymentStatus.PAID
                    );


            appointment
                    .setAppointmentStatus(
                            AppointmentStatus.CONFIRMED
                    );


            appointment
                    .setPaidAt(
                            LocalDateTime.now()
                    );


            appointmentRepository
                    .save(
                            appointment
                    );


            /*
             * Reuse our existing DTO.
             * No PaymentVerificationResponse DTO.
             */
            return appointmentService
                    .getAppointmentById(
                            appointmentId,
                            email
                    );


        } catch (
                RazorpayException exception
        ) {

            throw new PaymentException(
                    "Unable to verify payment: "
                            + getRazorpayMessage(
                            exception
                    )
            );
        }
    }


    // -------------------------------------------------
    // HELPERS
    // -------------------------------------------------

    private User findUserByEmail(
            String email) {

        return userRepository
                .findByEmailIgnoreCase(
                        email
                )
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );
    }


    private Appointment findAppointmentById(
            Long appointmentId) {

        return appointmentRepository
                .findById(
                        appointmentId
                )
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found with id: "
                                        + appointmentId
                        )
                );
    }


    private void verifyOwnership(
            Appointment appointment,
            User patient) {

        if (
                !appointment
                        .getPatient()
                        .getId()
                        .equals(
                                patient.getId()
                        )
        ) {

            throw new AccessDeniedException(
                    "You cannot make payment for this appointment"
            );
        }
    }


    private void validateForPayment(
            Appointment appointment) {

        if (
                appointment
                        .getAppointmentStatus()
                        !=
                        AppointmentStatus.PENDING
        ) {

            throw new IllegalArgumentException(
                    "Only pending appointments can be paid"
            );
        }


        if (
                appointment
                        .getPaymentStatus()
                        !=
                        PaymentStatus.UNPAID
        ) {

            throw new IllegalArgumentException(
                    "Appointment is already paid or unavailable for payment"
            );
        }
    }


    private long convertToPaise(
            BigDecimal amount) {

        return amount
                .movePointRight(2)
                .longValueExact();
    }


    private String getRazorpayMessage(
            RazorpayException exception) {

        String description =
                exception.getDescription();


        if (
                description != null
                        &&
                        !description.isBlank()
        ) {

            return description;
        }


        return exception.getMessage();
    }
}
