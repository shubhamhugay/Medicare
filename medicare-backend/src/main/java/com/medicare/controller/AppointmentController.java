package com.medicare.controller;

import java.util.List;

import com.medicare.dto.AppointmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.medicare.dto.AppointmentRequest;
import com.medicare.entity.Appointment;
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
    @GetMapping
    public ResponseEntity<List<Appointment>>
    getAllAppointments() {

        return ResponseEntity.ok(
                appointmentService
                        .getAllAppointments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment>
    getAppointmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentById(id)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>>
    getAppointmentsByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentsByPatient(
                                patientId
                        )
        );
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>>
    getAppointmentsByDoctor(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentsByDoctor(
                                doctorId
                        )
        );
    }

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