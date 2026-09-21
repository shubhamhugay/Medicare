package com.medicare.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Appointment>
    createAppointment(
            @Valid
            @RequestBody
            AppointmentRequest request) {

        Appointment appointment =
                appointmentService
                        .createAppointment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointment);
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
}