package com.medicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}