package com.medicare.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "appointment_id",
            nullable = false,
            unique = true
    )
    private Appointment appointment;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String diagnosis;

    @Column(
            name = "doctor_notes",
            columnDefinition = "TEXT"
    )
    private String doctorNotes;

    @OneToMany(
            mappedBy = "prescription",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PrescriptionMedicine> medicines =
            new ArrayList<>();

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt =
            LocalDateTime.now();

    public Prescription() {
    }

    public Long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(
            Appointment appointment) {

        this.appointment = appointment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(
            String diagnosis) {

        this.diagnosis = diagnosis;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public void setDoctorNotes(
            String doctorNotes) {

        this.doctorNotes = doctorNotes;
    }

    public List<PrescriptionMedicine> getMedicines() {
        return medicines;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void addMedicine(
            PrescriptionMedicine medicine) {

        medicines.add(medicine);

        medicine.setPrescription(this);
    }
}