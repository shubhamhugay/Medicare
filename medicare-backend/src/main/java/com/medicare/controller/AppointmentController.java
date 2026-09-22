package com.medicare.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.dto.AppointmentRequest;
import com.medicare.dto.AppointmentResponse;
import com.medicare.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(
            AppointmentService appointmentService) {

        this.appointmentService =
                appointmentService;
    }


    // -------------------------------------------------
    // PATIENT - BOOK APPOINTMENT
    // -------------------------------------------------

    @PostMapping
    public ResponseEntity<AppointmentResponse>
    createAppointment(
            @Valid
            @RequestBody
            AppointmentRequest request,
            Authentication authentication) {

        AppointmentResponse response =
                appointmentService
                        .createAppointment(
                                request,
                                authentication.getName()
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // -------------------------------------------------
    // PATIENT - VIEW OWN APPOINTMENTS
    // -------------------------------------------------

    @GetMapping("/my")
    public ResponseEntity<List<AppointmentResponse>>
    getMyAppointments(
            Authentication authentication) {

        return ResponseEntity.ok(
                appointmentService
                        .getMyAppointments(
                                authentication.getName()
                        )
        );
    }


    // -------------------------------------------------
    // DOCTOR - VIEW OWN APPOINTMENTS
    // -------------------------------------------------

    @GetMapping("/doctor/me")
    public ResponseEntity<List<AppointmentResponse>>
    getMyDoctorAppointments(
            Authentication authentication) {

        return ResponseEntity.ok(
                appointmentService
                        .getMyDoctorAppointments(
                                authentication.getName()
                        )
        );
    }


    // -------------------------------------------------
    // PATIENT / DOCTOR - VIEW APPOINTMENT DETAILS
    // -------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse>
    getAppointmentById(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentById(
                                id,
                                authentication.getName()
                        )
        );
    }


    // -------------------------------------------------
    // PATIENT - CANCEL OWN APPOINTMENT
    // -------------------------------------------------

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse>
    cancelAppointment(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                appointmentService
                        .cancelAppointment(
                                id,
                                authentication.getName()
                        )
        );
    }


    // -------------------------------------------------
    // DOCTOR - COMPLETE ASSIGNED APPOINTMENT
    // -------------------------------------------------

    @PatchMapping("/{id}/complete")
    public ResponseEntity<AppointmentResponse>
    completeAppointment(
            @PathVariable Long id,
            Authentication authentication) {

        return ResponseEntity.ok(
                appointmentService
                        .completeAppointment(
                                id,
                                authentication.getName()
                        )
        );
    }
}