package com.medicare.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.medicare.dto.DoctorPageResponse;
import com.medicare.dto.DoctorProfileRequest;
import com.medicare.dto.DoctorResponse;
import com.medicare.entity.DoctorProfile;
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


    // -------------------------------------------------
    // SEARCH / FILTER / PAGINATION
    // -------------------------------------------------

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

        validatePagination(page, size);

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


    // -------------------------------------------------
    // GET DOCTOR
    // -------------------------------------------------

    public DoctorResponse getDoctorById(
            Long id) {

        DoctorProfile doctor =
                findDoctorById(id);

        return mapToDoctorResponse(doctor);
    }


    // -------------------------------------------------
    // DOCTOR PROFILE MANAGEMENT
    // -------------------------------------------------

    @PreAuthorize("hasRole('DOCTOR')")
    public DoctorResponse createMyProfile(
            DoctorProfileRequest request,
            String email) {

        User user =
                findUserByEmail(email);

        if (doctorRepository
                .existsByUserId(user.getId())) {

            throw new IllegalArgumentException(
                    "Doctor profile already exists"
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

        return mapToDoctorResponse(
                savedDoctor
        );
    }


    @PreAuthorize("hasRole('DOCTOR')")
    public DoctorResponse getMyProfile(
            String email) {

        User user =
                findUserByEmail(email);

        DoctorProfile doctor =
                findDoctorByUserId(
                        user.getId()
                );

        return mapToDoctorResponse(
                doctor
        );
    }


    @PreAuthorize("hasRole('DOCTOR')")
    public DoctorResponse updateMyProfile(
            String email,
            DoctorProfileRequest request) {

        User user =
                findUserByEmail(email);

        DoctorProfile doctor =
                findDoctorByUserId(
                        user.getId()
                );

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

        return mapToDoctorResponse(
                updatedDoctor
        );
    }


    @PreAuthorize("hasRole('DOCTOR')")
    public void deleteMyProfile(
            String email) {

        User user =
                findUserByEmail(email);

        DoctorProfile doctor =
                findDoctorByUserId(
                        user.getId()
                );

        doctorRepository.delete(doctor);
    }


    // -------------------------------------------------
    // HELPER METHODS
    // -------------------------------------------------

    private DoctorProfile findDoctorById(
            Long id) {

        return doctorRepository
                .findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: "
                                        + id
                        )
                );
    }


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


    private String normalizeText(
            String value) {

        if (value == null
                || value.isBlank()) {

            return null;
        }

        return value.trim();
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
}