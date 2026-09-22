package com.medicare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.entity.Prescription;

public interface PrescriptionRepository
        extends JpaRepository<Prescription, Long> {

    boolean existsByAppointmentId(
            Long appointmentId
    );

    Optional<Prescription>
    findByAppointmentId(
            Long appointmentId
    );

    List<Prescription>
    findByAppointment_Patient_Id(
            Long patientId
    );
}