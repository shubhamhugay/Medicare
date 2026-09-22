package com.medicare.repository;

import java.time.LocalDate;
import java.util.List;

import com.medicare.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.entity.Appointment;
import com.medicare.entity.TimeSlot;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(
            Long patientId
    );

    List<Appointment> findByDoctorId(
            Long doctorId
    );

    boolean
    existsByDoctorIdAndAppointmentDateAndTimeSlotAndAppointmentStatusNot(
            Long doctorId,
            LocalDate appointmentDate,
            TimeSlot timeSlot,
            AppointmentStatus appointmentStatus
    );
}