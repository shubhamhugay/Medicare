package com.medicare.service;

import java.util.ArrayList;
import java.util.List;

import com.medicare.dto.AppointmentResponse;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('PATIENT')")
    public AppointmentResponse createAppointment(
            AppointmentRequest request,
            String email) {

        User patient =
                findUserByEmail(email);

        DoctorProfile doctor =
                doctorRepository
                        .findById(
                                request.getDoctorId()
                        )
                        .orElseThrow(() ->
                                new DoctorNotFoundException(
                                        "Doctor not found with id: "
                                                + request.getDoctorId()
                                )
                        );

        boolean booked =
                appointmentRepository
                        .existsByDoctorIdAndAppointmentDateAndTimeSlotAndAppointmentStatusNot(
                                doctor.getId(),
                                request.getAppointmentDate(),
                                request.getTimeSlot(),
                                AppointmentStatus.CANCELLED
                        );

        if (booked) {

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

        appointment.setConsultationFee(
                doctor.getConsultationFee()
        );

        appointment.setAppointmentStatus(
                AppointmentStatus.PENDING
        );

        appointment.setPaymentStatus(
                PaymentStatus.UNPAID
        );

        Appointment savedAppointment =
                appointmentRepository
                        .save(appointment);

        return mapToAppointmentResponse(
                savedAppointment
        );
    }
    public List<Appointment> getAllAppointments() {

        return appointmentRepository.findAll();
    }
    @PreAuthorize("hasRole('PATIENT')")
    public List<AppointmentResponse>
    getMyAppointments(
            String email) {

        User patient =
                findUserByEmail(email);

        List<Appointment> appointments =
                appointmentRepository
                        .findByPatientId(
                                patient.getId()
                        );

        return mapAppointmentList(
                appointments
        );
    }

    @PreAuthorize("hasRole('DOCTOR')")
    public List<AppointmentResponse>
    getMyDoctorAppointments(
            String email) {

        User user =
                findUserByEmail(email);

        DoctorProfile doctor =
                findDoctorByUserId(
                        user.getId()
                );

        List<Appointment> appointments =
                appointmentRepository
                        .findByDoctorId(
                                doctor.getId()
                        );

        return mapAppointmentList(
                appointments
        );
    }

    @PreAuthorize(
            "hasAnyRole('PATIENT', 'DOCTOR')"
    )
    public AppointmentResponse
    getAppointmentById(
            Long appointmentId,
            String email) {

        Appointment appointment =
                findAppointmentById(
                        appointmentId
                );

        User user =
                findUserByEmail(email);

        verifyAppointmentAccess(
                appointment,
                user
        );

        return mapToAppointmentResponse(
                appointment
        );
    }
    @PreAuthorize("hasRole('PATIENT')")
    public AppointmentResponse cancelAppointment(
            Long appointmentId,
            String email) {

        User patient =
                findUserByEmail(email);

        Appointment appointment =
                findAppointmentById(
                        appointmentId
                );

        if (!appointment
                .getPatient()
                .getId()
                .equals(patient.getId())) {

            throw new AccessDeniedException(
                    "You cannot cancel this appointment"
            );
        }

        if (appointment.getAppointmentStatus()
                == AppointmentStatus.COMPLETED) {

            throw new IllegalArgumentException(
                    "Completed appointment cannot be cancelled"
            );
        }

        if (appointment.getAppointmentStatus()
                == AppointmentStatus.CANCELLED) {

            throw new IllegalArgumentException(
                    "Appointment is already cancelled"
            );
        }

        appointment.setAppointmentStatus(
                AppointmentStatus.CANCELLED
        );

        return mapToAppointmentResponse(
                appointmentRepository
                        .save(appointment)
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


    @PreAuthorize("hasRole('DOCTOR')")
    public AppointmentResponse completeAppointment(
            Long appointmentId,
            String email) {

        User user =
                findUserByEmail(email);

        DoctorProfile doctor =
                findDoctorByUserId(
                        user.getId()
                );

        Appointment appointment =
                findAppointmentById(
                        appointmentId
                );

        if (!appointment
                .getDoctor()
                .getId()
                .equals(doctor.getId())) {

            throw new AccessDeniedException(
                    "You cannot complete this appointment"
            );
        }

        if (appointment.getAppointmentStatus()
                == AppointmentStatus.CANCELLED) {

            throw new IllegalArgumentException(
                    "Cancelled appointment cannot be completed"
            );
        }

        appointment.setAppointmentStatus(
                AppointmentStatus.COMPLETED
        );

        return mapToAppointmentResponse(
                appointmentRepository
                        .save(appointment)
        );
    }

    private List<AppointmentResponse>
    mapAppointmentList(
            List<Appointment> appointments) {

        List<AppointmentResponse> responses =
                new ArrayList<>();

        for (Appointment appointment
                : appointments) {

            responses.add(
                    mapToAppointmentResponse(
                            appointment
                    )
            );
        }

        return responses;
    }
    private AppointmentResponse
    mapToAppointmentResponse(
            Appointment appointment) {

        return new AppointmentResponse(
                appointment.getId(),

                appointment
                        .getPatient()
                        .getId(),

                appointment
                        .getPatient()
                        .getName(),

                appointment
                        .getDoctor()
                        .getId(),

                appointment
                        .getDoctor()
                        .getUser()
                        .getName(),

                appointment
                        .getDoctor()
                        .getSpecialization(),

                appointment.getAppointmentDate(),
                appointment.getTimeSlot(),
                appointment.getConsultationFee(),
                appointment.getAppointmentStatus(),
                appointment.getPaymentStatus(),
                appointment.getCreatedAt()
        );
    }
}