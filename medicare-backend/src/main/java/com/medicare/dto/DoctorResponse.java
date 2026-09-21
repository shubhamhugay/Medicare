package com.medicare.dto;

import java.math.BigDecimal;

public class DoctorResponse {

    private Long id;
    private String name;
    private String specialization;
    private Integer experienceYears;
    private BigDecimal consultationFee;
    private String photoUrl;

    public DoctorResponse(
            Long id,
            String name,
            String specialization,
            Integer experienceYears,
            BigDecimal consultationFee,
            String photoUrl) {

        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.consultationFee = consultationFee;
        this.photoUrl = photoUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}