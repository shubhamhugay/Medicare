package com.medicare.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "patient_id",
            nullable = false
    )
    private User patient;

    @ManyToOne
    @JoinColumn(
            name = "doctor_id",
            nullable = false
    )
    private DoctorProfile doctor;

    @Column(
            name = "appointment_date",
            nullable = false
    )
    private LocalDate appointmentDate;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "time_slot",
            nullable = false
    )
    private TimeSlot timeSlot;

    @Column(
            name = "consultation_fee",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal consultationFee;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "appointment_status",
            nullable = false
    )
    private AppointmentStatus appointmentStatus =
            AppointmentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "payment_status",
            nullable = false
    )
    private PaymentStatus paymentStatus =
            PaymentStatus.UNPAID;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt =
            LocalDateTime.now();

    public Appointment() {
    }

    public Long getId() {
        return id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public DoctorProfile getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorProfile doctor) {
        this.doctor = doctor;
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

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(
            BigDecimal consultationFee) {

        this.consultationFee = consultationFee;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(
            AppointmentStatus appointmentStatus) {

        this.appointmentStatus = appointmentStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(
            PaymentStatus paymentStatus) {

        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}