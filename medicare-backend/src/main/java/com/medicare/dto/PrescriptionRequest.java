package com.medicare.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PrescriptionRequest {

    @NotNull(
            message = "Appointment id is required"
    )
    private Long appointmentId;

    @NotBlank(
            message = "Diagnosis is required"
    )
    @Size(
            max = 2000,
            message = "Diagnosis is too long"
    )
    private String diagnosis;

    @Size(
            max = 3000,
            message = "Doctor notes are too long"
    )
    private String doctorNotes;

    @NotEmpty(
            message = "At least one medicine is required"
    )
    @Valid
    private List<MedicineDto> medicines;

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(
            Long appointmentId) {

        this.appointmentId = appointmentId;
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

    public List<MedicineDto> getMedicines() {
        return medicines;
    }

    public void setMedicines(
            List<MedicineDto> medicines) {

        this.medicines = medicines;
    }
}