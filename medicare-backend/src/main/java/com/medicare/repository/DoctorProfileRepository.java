package com.medicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.entity.DoctorProfile;

public interface DoctorProfileRepository
        extends JpaRepository<DoctorProfile, Long> {

    boolean existsByUserId(Long userId);
}