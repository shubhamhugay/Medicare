package com.medicare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.medicare.dto.MedicineDto;
import com.medicare.dto.PrescriptionRequest;
import com.medicare.dto.PrescriptionResponse;
import com.medicare.entity.Appointment;
import com.medicare.entity.AppointmentStatus;
import com.medicare.entity.DoctorProfile;
import com.medicare.entity.Prescription;
import com.medicare.entity.PrescriptionMedicine;
import com.medicare.entity.Role;
import com.medicare.entity.User;
import com.medicare.exception.AppointmentNotFoundException;
import com.medicare.exception.DoctorNotFoundException;
import com.medicare.exception.PrescriptionConflictException;
import com.medicare.exception.PrescriptionNotFoundException;
import com.medicare.exception.UserNotFoundException;
import com.medicare.repository.AppointmentRepository;
import com.medicare.repository.DoctorProfileRepository;
import com.medicare.repository.PrescriptionRepository;
import com.medicare.repository.UserRepository;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorRepository;

    public PrescriptionService(
            PrescriptionRepository prescriptionRepository,
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            DoctorProfileRepository doctorRepository) {

        this.prescriptionRepository =
                prescriptionRepository;

        this.appointmentRepository =
                appointmentRepository;

        this.userRepository =
                userRepository;

        this.doctorRepository =
                doctorRepository;
    }


    // -------------------------------------------------
    // DOCTOR - CREATE PRESCRIPTION
    // -------------------------------------------------

    @Transactional
    @PreAuthorize("hasRole('DOCTOR')")
    public PrescriptionResponse createPrescription(
            PrescriptionRequest request,
            String email) {

        User user =
                findUserByEmail(email);

        DoctorProfile doctor =
                findDoctorByUserId(
                        user.getId()
                );

        Appointment appointment =
                findAppointmentById(
                        request.getAppointmentId()
                );

        /*
         * Only the doctor assigned to this
         * appointment can create its prescription.
         */
        if (!appointment
                .getDoctor()
                .getId()
                .equals(doctor.getId())) {

            throw new AccessDeniedException(
                    "You cannot create a prescription for this appointment"
            );
        }

        /*
         * Prescription can only be created
         * after consultation is completed.
         */
        if (appointment.getAppointmentStatus()
                != AppointmentStatus.COMPLETED) {

            throw new IllegalArgumentException(
                    "Prescription can only be created for a completed appointment"
            );
        }

        /*
         * Only one prescription is allowed
         * for one appointment.
         */
        if (prescriptionRepository
                .existsByAppointmentId(
                        appointment.getId()
                )) {

            throw new PrescriptionConflictException(
                    "Prescription already exists for this appointment"
            );
        }

        Prescription prescription =
                new Prescription();

        prescription.setAppointment(
                appointment
        );

        prescription.setDiagnosis(
                request.getDiagnosis()
        );

        prescription.setDoctorNotes(
                request.getDoctorNotes()
        );

        for (MedicineDto medicineDto
                : request.getMedicines()) {

            PrescriptionMedicine medicine =
                    new PrescriptionMedicine();

            medicine.setMedicineName(
                    medicineDto.getMedicineName()
            );

            medicine.setDosage(
                    medicineDto.getDosage()
            );

            medicine.setFrequency(
                    medicineDto.getFrequency()
            );

            medicine.setDuration(
                    medicineDto.getDuration()
            );

            prescription.addMedicine(
                    medicine
            );
        }

        Prescription savedPrescription =
                prescriptionRepository
                        .save(prescription);

        return mapToPrescriptionResponse(
                savedPrescription
        );
    }


    // -------------------------------------------------
    // VIEW PRESCRIPTION FOR APPOINTMENT
    // -------------------------------------------------

    @Transactional(readOnly = true)
    @PreAuthorize(
            "hasAnyRole('PATIENT', 'DOCTOR')"
    )
    public PrescriptionResponse
    getPrescriptionByAppointment(
            Long appointmentId,
            String email) {

        Prescription prescription =
                prescriptionRepository
                        .findByAppointmentId(
                                appointmentId
                        )
                        .orElseThrow(() ->
                                new PrescriptionNotFoundException(
                                        "Prescription not found for appointment id: "
                                                + appointmentId
                                )
                        );

        User user =
                findUserByEmail(email);

        verifyPrescriptionAccess(
                prescription,
                user
        );

        return mapToPrescriptionResponse(
                prescription
        );
    }


    // -------------------------------------------------
    // PATIENT - VIEW OWN PRESCRIPTIONS
    // -------------------------------------------------

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('PATIENT')")
    public List<PrescriptionResponse>
    getMyPrescriptions(
            String email) {

        User patient =
                findUserByEmail(email);

        List<Prescription> prescriptions =
                prescriptionRepository
                        .findByAppointment_Patient_Id(
                                patient.getId()
                        );

        List<PrescriptionResponse> responses =
                new ArrayList<>();

        for (Prescription prescription
                : prescriptions) {

            responses.add(
                    mapToPrescriptionResponse(
                            prescription
                    )
            );
        }

        return responses;
    }


    // -------------------------------------------------
    // OWNERSHIP CHECK
    // -------------------------------------------------

    private void verifyPrescriptionAccess(
            Prescription prescription,
            User user) {

        Appointment appointment =
                prescription.getAppointment();

        if (user.getRole() == Role.PATIENT) {

            if (!appointment
                    .getPatient()
                    .getId()
                    .equals(user.getId())) {

                throw new AccessDeniedException(
                        "You cannot access this prescription"
                );
            }

            return;
        }

        if (user.getRole() == Role.DOCTOR) {

            DoctorProfile doctor =
                    findDoctorByUserId(
                            user.getId()
                    );

            if (!appointment
                    .getDoctor()
                    .getId()
                    .equals(doctor.getId())) {

                throw new AccessDeniedException(
                        "You cannot access this prescription"
                );
            }
        }
    }


    // -------------------------------------------------
    // HELPER METHODS
    // -------------------------------------------------

    private User findUserByEmail(
            String email) {

        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );
    }


    private DoctorProfile findDoctorByUserId(
            Long userId) {

        return doctorRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor profile not found"
                        )
                );
    }


    private Appointment findAppointmentById(
            Long appointmentId) {

        return appointmentRepository
                .findById(appointmentId)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found with id: "
                                        + appointmentId
                        )
                );
    }


    // -------------------------------------------------
    // RESPONSE MAPPING
    // -------------------------------------------------

    private PrescriptionResponse
    mapToPrescriptionResponse(
            Prescription prescription) {

        List<MedicineDto> medicines =
                new ArrayList<>();

        for (PrescriptionMedicine medicine
                : prescription.getMedicines()) {

            MedicineDto medicineDto =
                    new MedicineDto(
                            medicine.getMedicineName(),
                            medicine.getDosage(),
                            medicine.getFrequency(),
                            medicine.getDuration()
                    );

            medicines.add(
                    medicineDto
            );
        }

        Appointment appointment =
                prescription.getAppointment();

        return new PrescriptionResponse(
                prescription.getId(),

                appointment.getId(),

                appointment
                        .getDoctor()
                        .getId(),

                appointment
                        .getDoctor()
                        .getUser()
                        .getName(),

                appointment
                        .getPatient()
                        .getId(),

                appointment
                        .getPatient()
                        .getName(),

                prescription.getDiagnosis(),

                prescription.getDoctorNotes(),

                medicines,

                prescription.getCreatedAt()
        );
    }
}