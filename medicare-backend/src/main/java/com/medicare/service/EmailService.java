package com.medicare.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Service;

import com.medicare.dto.AppointmentResponse;
import com.medicare.entity.TimeSlot;


@Service
public class EmailService {


    private static final Logger logger =
            LoggerFactory.getLogger(
                    EmailService.class
            );


    private final JavaMailSender
            mailSender;


    @Value("${spring.mail.username}")
    private String fromEmail;


    public EmailService(
            JavaMailSender mailSender) {

        this.mailSender =
                mailSender;
    }


    // -------------------------------------------------
    // APPOINTMENT CONFIRMATION EMAIL
    // -------------------------------------------------

    @Async
    public void
    sendAppointmentConfirmation(
            String patientEmail,
            AppointmentResponse appointment) {


        try {

            SimpleMailMessage message =
                    new SimpleMailMessage();


            message.setFrom(
                    fromEmail
            );


            message.setTo(
                    patientEmail
            );


            message.setSubject(
                    "Medicare Appointment Confirmation"
            );


            message.setText(
                    buildAppointmentEmail(
                            appointment
                    )
            );


            mailSender.send(
                    message
            );


            logger.info(
                    "Appointment confirmation email sent for appointment {}",
                    appointment.getId()
            );


        } catch (
                MailException exception
        ) {

            /*
             * Email failure must NOT
             * reverse successful payment.
             */

            logger.error(
                    "Unable to send confirmation email for appointment {}: {}",
                    appointment.getId(),
                    exception.getMessage()
            );
        }
    }


    // -------------------------------------------------
    // EMAIL BODY
    // -------------------------------------------------

    private String
    buildAppointmentEmail(
            AppointmentResponse appointment) {


        return """
                Hello %s,

                Your Medicare appointment has been confirmed successfully.

                Appointment Details

                Appointment ID: %d
                Doctor: %s
                Specialization: %s
                Date: %s
                Time: %s
                Consultation Fee: ₹%s
                Appointment Status: %s
                Payment Status: %s

                Please arrive on time for your consultation.

                Thank you,
                Medicare Healthcare
                """
                .formatted(
                        appointment
                                .getPatientName(),

                        appointment
                                .getId(),

                        appointment
                                .getDoctorName(),

                        appointment
                                .getSpecialization(),

                        appointment
                                .getAppointmentDate(),

                        formatTimeSlot(
                                appointment
                                        .getTimeSlot()
                        ),

                        appointment
                                .getConsultationFee(),

                        appointment
                                .getAppointmentStatus(),

                        appointment
                                .getPaymentStatus()
                );
    }


    // -------------------------------------------------
    // FORMAT TIME SLOT
    // -------------------------------------------------

    private String formatTimeSlot(
            TimeSlot timeSlot) {


        if (timeSlot == null) {

            return "";
        }


        return switch (timeSlot) {

            case MORNING_10_AM ->
                    "10:00 AM";

            case AFTERNOON_2_PM ->
                    "2:00 PM";

            case EVENING_5_PM ->
                    "5:00 PM";
        };
    }
}