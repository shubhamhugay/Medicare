package com.medicare.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.medicare.entity.AppointmentStatus;
import com.medicare.entity.PaymentStatus;
import com.medicare.entity.TimeSlot;

public class AppointmentResponse {

    private Long id;

    private Long patientId;
    private String patientName;

    private Long doctorId;
    private String doctorName;
    private String specialization;

    private LocalDate appointmentDate;
    private TimeSlot timeSlot;

    private BigDecimal consultationFee;

    private AppointmentStatus appointmentStatus;
    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;

    public AppointmentResponse(
            Long id,
            Long patientId,
            String patientName,
            Long doctorId,
            String doctorName,
            String specialization,
            LocalDate appointmentDate,
            TimeSlot timeSlot,
            BigDecimal consultationFee,
            AppointmentStatus appointmentStatus,
            PaymentStatus paymentStatus,
            LocalDateTime createdAt) {

        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.appointmentDate = appointmentDate;
        this.timeSlot = timeSlot;
        this.consultationFee = consultationFee;
        this.appointmentStatus = appointmentStatus;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}