package com.medicare.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.medicare.dto.DoctorProfileRequest;
import com.medicare.dto.DoctorResponse;
import com.medicare.entity.DoctorProfile;
import com.medicare.entity.Role;
import com.medicare.entity.User;
import com.medicare.exception.DoctorNotFoundException;
import com.medicare.exception.UserNotFoundException;
import com.medicare.repository.DoctorProfileRepository;
import com.medicare.repository.UserRepository;

@Service
public class DoctorService {

    private final DoctorProfileRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorService(
            DoctorProfileRepository doctorRepository,
            UserRepository userRepository) {

        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    public DoctorResponse createDoctor(
            DoctorProfileRequest request) {

        User user = findUserById(
                request.getUserId()
        );

        if (user.getRole() != Role.DOCTOR) {

            throw new IllegalArgumentException(
                    "Doctor profile can only be created for a DOCTOR user"
            );
        }

        if (doctorRepository.existsByUserId(user.getId())) {

            throw new IllegalArgumentException(
                    "Doctor profile already exists for this user"
            );
        }

        DoctorProfile doctor =
                new DoctorProfile();

        doctor.setUser(user);

        doctor.setSpecialization(
                request.getSpecialization()
        );

        doctor.setExperienceYears(
                request.getExperienceYears()
        );

        doctor.setConsultationFee(
                request.getConsultationFee()
        );

        doctor.setPhotoUrl(
                request.getPhotoUrl()
        );

        DoctorProfile savedDoctor =
                doctorRepository.save(doctor);

        return mapToDoctorResponse(savedDoctor);
    }

    public List<DoctorResponse> searchDoctors(
            String specialization,
            BigDecimal maxFee,
            String name) {

        specialization =
                normalizeText(specialization);

        name =
                normalizeText(name);

        if (maxFee != null
                && maxFee.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Maximum consultation fee must be greater than zero"
            );
        }

        List<DoctorProfile> doctors =
                doctorRepository.searchDoctors(
                        specialization,
                        maxFee,
                        name
                );

        List<DoctorResponse> responses =
                new ArrayList<>();

        for (DoctorProfile doctor : doctors) {

            responses.add(
                    mapToDoctorResponse(doctor)
            );
        }

        return responses;
    }

    public DoctorResponse getDoctorById(
            Long id) {

        DoctorProfile doctor =
                findDoctorById(id);

        return mapToDoctorResponse(doctor);
    }

    public DoctorResponse updateDoctor(
            Long id,
            DoctorProfileRequest request) {

        DoctorProfile doctor =
                findDoctorById(id);

        doctor.setSpecialization(
                request.getSpecialization()
        );

        doctor.setExperienceYears(
                request.getExperienceYears()
        );

        doctor.setConsultationFee(
                request.getConsultationFee()
        );

        doctor.setPhotoUrl(
                request.getPhotoUrl()
        );

        DoctorProfile updatedDoctor =
                doctorRepository.save(doctor);

        return mapToDoctorResponse(updatedDoctor);
    }

    public void deleteDoctor(Long id) {

        DoctorProfile doctor =
                findDoctorById(id);

        doctorRepository.delete(doctor);
    }

    private DoctorProfile findDoctorById(
            Long id) {

        return doctorRepository
                .findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + id
                        )
                );
    }

    private User findUserById(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }

    /*
     * Converts DoctorProfile entity into the simpler
     * response sent to the frontend.
     */
    private DoctorResponse mapToDoctorResponse(
            DoctorProfile doctor) {

        return new DoctorResponse(
                doctor.getId(),
                doctor.getUser().getName(),
                doctor.getSpecialization(),
                doctor.getExperienceYears(),
                doctor.getConsultationFee(),
                doctor.getPhotoUrl()
        );
    }

    /*
     * Empty request parameters should behave
     * the same as parameters that were not supplied.
     */
    private String normalizeText(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}