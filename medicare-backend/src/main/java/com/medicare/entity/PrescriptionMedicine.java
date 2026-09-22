package com.medicare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "prescription_medicines")
public class PrescriptionMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "prescription_id",
            nullable = false
    )
    private Prescription prescription;

    @Column(
            name = "medicine_name",
            nullable = false
    )
    private String medicineName;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private String duration;

    public PrescriptionMedicine() {
    }

    public Long getId() {
        return id;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(
            Prescription prescription) {

        this.prescription = prescription;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(
            String medicineName) {

        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(
            String dosage) {

        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(
            String frequency) {

        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(
            String duration) {

        this.duration = duration;
    }
}