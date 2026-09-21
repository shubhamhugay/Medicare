package com.medicare.dto;

import java.time.LocalDate;

import com.medicare.entity.TimeSlot;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class AppointmentRequest {

    @NotNull(message = "Patient id is required")
    private Long patientId;

    @NotNull(message = "Doctor id is required")
    private Long doctorId;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(
            message = "Appointment date cannot be in the past"
    )
    private LocalDate appointmentDate;

    @NotNull(message = "Time slot is required")
    private TimeSlot timeSlot;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(
            LocalDate appointmentDate) {

        this.appointmentDate = appointmentDate;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
}