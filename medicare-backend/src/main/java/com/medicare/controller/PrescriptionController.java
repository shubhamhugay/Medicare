package com.medicare.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.dto.PrescriptionRequest;
import com.medicare.dto.PrescriptionResponse;
import com.medicare.service.PrescriptionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(
            PrescriptionService prescriptionService) {

        this.prescriptionService =
                prescriptionService;
    }


    // -------------------------------------------------
    // DOCTOR - CREATE PRESCRIPTION
    // -------------------------------------------------

    @PostMapping
    public ResponseEntity<PrescriptionResponse>
    createPrescription(
            @Valid
            @RequestBody
            PrescriptionRequest request,
            Authentication authentication) {

        PrescriptionResponse response =
                prescriptionService
                        .createPrescription(
                                request,
                                authentication.getName()
                        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // -------------------------------------------------
    // PATIENT / DOCTOR - VIEW BY APPOINTMENT
    // -------------------------------------------------

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<PrescriptionResponse>
    getPrescriptionByAppointment(
            @PathVariable Long appointmentId,
            Authentication authentication) {

        return ResponseEntity.ok(
                prescriptionService
                        .getPrescriptionByAppointment(
                                appointmentId,
                                authentication.getName()
                        )
        );
    }


    // -------------------------------------------------
    // PATIENT - VIEW OWN PRESCRIPTIONS
    // -------------------------------------------------

    @GetMapping("/my")
    public ResponseEntity<List<PrescriptionResponse>>
    getMyPrescriptions(
            Authentication authentication) {

        return ResponseEntity.ok(
                prescriptionService
                        .getMyPrescriptions(
                                authentication.getName()
                        )
        );
    }
}