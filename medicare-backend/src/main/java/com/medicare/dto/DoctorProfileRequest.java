package com.medicare.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class DoctorProfileRequest {

    @NotBlank(message = "Specialization is required")
    @Size(max = 100)
    private String specialization;

    @NotNull(message = "Experience is required")
    @Min(0)
    @Max(60)
    private Integer experienceYears;

    @NotNull(message = "Consultation fee is required")
    @Positive
    private BigDecimal consultationFee;

    @Size(max = 500)
    private String photoUrl;

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(
            String specialization) {

        this.specialization = specialization;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(
            Integer experienceYears) {

        this.experienceYears = experienceYears;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(
            BigDecimal consultationFee) {

        this.consultationFee = consultationFee;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(
            String photoUrl) {

        this.photoUrl = photoUrl;
    }
}