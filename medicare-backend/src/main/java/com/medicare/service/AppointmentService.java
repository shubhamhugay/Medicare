package com.medicare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medicare.dto.AppointmentRequest;
import com.medicare.entity.Appointment;
import com.medicare.entity.AppointmentStatus;
import com.medicare.entity.DoctorProfile;
import com.medicare.entity.PaymentStatus;
import com.medicare.entity.Role;
import com.medicare.entity.User;
import com.medicare.exception.AppointmentConflictException;
import com.medicare.exception.AppointmentNotFoundException;
import com.medicare.exception.DoctorNotFoundException;
import com.medicare.exception.UserNotFoundException;
import com.medicare.repository.AppointmentRepository;
import com.medicare.repository.DoctorProfileRepository;
import com.medicare.repository.UserRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            DoctorProfileRepository doctorRepository) {

        this.appointmentRepository =
                appointmentRepository;

        this.userRepository =
                userRepository;

        this.doctorRepository =
                doctorRepository;
    }

    public Appointment createAppointment(
            AppointmentRequest request) {

        User patient = userRepository
                .findById(request.getPatientId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Patient not found with id: "
                                        + request.getPatientId()
                        )
                );

        if (patient.getRole() != Role.PATIENT) {

            throw new IllegalArgumentException(
                    "Appointment can only be booked for a PATIENT user"
            );
        }

        DoctorProfile doctor =
                doctorRepository
                        .findById(request.getDoctorId())
                        .orElseThrow(() ->
                                new DoctorNotFoundException(
                                        "Doctor not found with id: "
                                                + request.getDoctorId()
                                )
                        );

        boolean slotAlreadyBooked =
                appointmentRepository
                        .existsByDoctorIdAndAppointmentDateAndTimeSlot(
                                doctor.getId(),
                                request.getAppointmentDate(),
                                request.getTimeSlot()
                        );

        if (slotAlreadyBooked) {

            throw new AppointmentConflictException(
                    "Selected appointment slot is already booked"
            );
        }

        Appointment appointment =
                new Appointment();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        appointment.setAppointmentDate(
                request.getAppointmentDate()
        );

        appointment.setTimeSlot(
                request.getTimeSlot()
        );

        /*
         * Consultation fee comes from the doctor's profile.
         * We do not trust the frontend to send the fee.
         */
        appointment.setConsultationFee(
                doctor.getConsultationFee()
        );

        appointment.setAppointmentStatus(
                AppointmentStatus.PENDING
        );

        appointment.setPaymentStatus(
                PaymentStatus.UNPAID
        );

        return appointmentRepository
                .save(appointment);
    }

    public List<Appointment> getAllAppointments() {

        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(
            Long id) {

        return appointmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found with id: " + id
                        )
                );
    }

    public List<Appointment>
    getAppointmentsByPatient(
            Long patientId) {

        return appointmentRepository
                .findByPatientId(patientId);
    }

    public List<Appointment>
    getAppointmentsByDoctor(
            Long doctorId) {

        return appointmentRepository
                .findByDoctorId(doctorId);
    }
}