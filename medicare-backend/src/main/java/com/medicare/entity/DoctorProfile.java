package com.medicare.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "doctor_profiles")
public class DoctorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private User user;

    @NotBlank(message = "Specialization is required")
    @Size(
            max = 100,
            message = "Specialization cannot exceed 100 characters"
    )
    @Column(nullable = false)
    private String specialization;

    @NotNull(message = "Experience is required")
    @Min(
            value = 0,
            message = "Experience cannot be negative"
    )
    @Max(
            value = 60,
            message = "Experience cannot exceed 60 years"
    )
    @Column(
            name = "experience_years",
            nullable = false
    )
    private Integer experienceYears;

    @NotNull(message = "Consultation fee is required")
    @Positive(
            message = "Consultation fee must be greater than zero"
    )
    @Column(
            name = "consultation_fee",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal consultationFee;

    @Size(
            max = 500,
            message = "Photo URL cannot exceed 500 characters"
    )
    @Column(name = "photo_url")
    private String photoUrl;

    public DoctorProfile() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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