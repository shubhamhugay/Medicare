package com.medicare.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.dto.AppointmentResponse;
import com.medicare.dto.PaymentOrderResponse;
import com.medicare.dto.PaymentVerificationRequest;

import com.medicare.service.PaymentService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {


    private final PaymentService
            paymentService;


    public PaymentController(
            PaymentService paymentService) {

        this.paymentService =
                paymentService;
    }


    // ---------------------------------------------
    // CREATE RAZORPAY ORDER
    // ---------------------------------------------

    @PostMapping(
            "/{appointmentId}/order"
    )
    public ResponseEntity<PaymentOrderResponse>
    createOrder(
            @PathVariable Long appointmentId,
            Authentication authentication) {


        return ResponseEntity.ok(
                paymentService
                        .createOrder(
                                appointmentId,
                                authentication
                                        .getName()
                        )
        );
    }


    // ---------------------------------------------
    // VERIFY RAZORPAY PAYMENT
    // ---------------------------------------------

    @PostMapping(
            "/{appointmentId}/verify"
    )
    public ResponseEntity<AppointmentResponse>
    verifyPayment(
            @PathVariable Long appointmentId,

            @Valid
            @RequestBody
            PaymentVerificationRequest request,

            Authentication authentication) {


        return ResponseEntity.ok(
                paymentService
                        .verifyPayment(
                                appointmentId,
                                request,
                                authentication
                                        .getName()
                        )
        );
    }
}