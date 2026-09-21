package com.medicare.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.medicare.dto.DoctorPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

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
    public DoctorPageResponse searchDoctors(
            String specialization,
            BigDecimal maxFee,
            String name,
            int page,
            int size,
            String sortBy,
            String direction) {

        specialization =
                normalizeText(specialization);

        name =
                normalizeText(name);

        validatePagination(
                page,
                size
        );

        if (maxFee != null
                && maxFee.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Maximum consultation fee must be greater than zero"
            );
        }

        String sortField =
                resolveSortField(sortBy);

        Sort.Direction sortDirection =
                resolveSortDirection(direction);

        Sort sort =
                Sort.by(
                        sortDirection,
                        sortField
                );

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        Page<DoctorProfile> doctorPage =
                doctorRepository.searchDoctors(
                        specialization,
                        maxFee,
                        name,
                        pageable
                );

        List<DoctorResponse> doctorResponses =
                new ArrayList<>();

        for (DoctorProfile doctor
                : doctorPage.getContent()) {

            doctorResponses.add(
                    mapToDoctorResponse(doctor)
            );
        }

        return new DoctorPageResponse(
                doctorResponses,
                doctorPage.getNumber(),
                doctorPage.getSize(),
                doctorPage.getTotalElements(),
                doctorPage.getTotalPages(),
                doctorPage.isLast()
        );
    }
    private void validatePagination(
            int page,
            int size) {

        if (page < 0) {

            throw new IllegalArgumentException(
                    "Page number cannot be negative"
            );
        }

        if (size < 1 || size > 50) {

            throw new IllegalArgumentException(
                    "Page size must be between 1 and 50"
            );
        }
    }
    private String resolveSortField(
            String sortBy) {

        if (sortBy == null
                || sortBy.isBlank()) {

            return "consultationFee";
        }

        return switch (sortBy) {

            case "name" ->
                    "user.name";

            case "specialization" ->
                    "specialization";

            case "experienceYears" ->
                    "experienceYears";

            case "consultationFee" ->
                    "consultationFee";

            default ->
                    throw new IllegalArgumentException(
                            "Invalid sort field"
                    );
        };
    }
    private Sort.Direction resolveSortDirection(
            String direction) {

        if (direction == null
                || direction.isBlank()
                || direction.equalsIgnoreCase("asc")) {

            return Sort.Direction.ASC;
        }

        if (direction.equalsIgnoreCase("desc")) {

            return Sort.Direction.DESC;
        }

        throw new IllegalArgumentException(
                "Sort direction must be asc or desc"
        );
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