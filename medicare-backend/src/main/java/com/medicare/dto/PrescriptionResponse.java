package com.medicare.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PrescriptionResponse {

    private Long id;

    private Long appointmentId;

    private Long doctorId;
    private String doctorName;

    private Long patientId;
    private String patientName;

    private String diagnosis;
    private String doctorNotes;

    private List<MedicineDto> medicines;

    private LocalDateTime createdAt;

    public PrescriptionResponse(
            Long id,
            Long appointmentId,
            Long doctorId,
            String doctorName,
            Long patientId,
            String patientName,
            String diagnosis,
            String doctorNotes,
            List<MedicineDto> medicines,
            LocalDateTime createdAt) {

        this.id = id;
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.diagnosis = diagnosis;
        this.doctorNotes = doctorNotes;
        this.medicines = medicines;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getDoctorNotes() {
        return doctorNotes;
    }

    public List<MedicineDto> getMedicines() {
        return medicines;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}