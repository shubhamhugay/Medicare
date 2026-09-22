package com.medicare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MedicineDto {

    @NotBlank(
            message = "Medicine name is required"
    )
    @Size(max = 150)
    private String medicineName;

    @NotBlank(
            message = "Dosage is required"
    )
    @Size(max = 100)
    private String dosage;

    @NotBlank(
            message = "Frequency is required"
    )
    @Size(max = 100)
    private String frequency;

    @NotBlank(
            message = "Duration is required"
    )
    @Size(max = 100)
    private String duration;

    public MedicineDto() {
    }

    public MedicineDto(
            String medicineName,
            String dosage,
            String frequency,
            String duration) {

        this.medicineName = medicineName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
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