package com.medicare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medicare.dto.DoctorProfileRequest;
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

    public DoctorProfile createDoctor(
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

        return doctorRepository.save(doctor);
    }

    public List<DoctorProfile> getAllDoctors() {

        return doctorRepository.findAll();
    }

    public DoctorProfile getDoctorById(Long id) {

        return findDoctorById(id);
    }

    public DoctorProfile updateDoctor(
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

        return doctorRepository.save(doctor);
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
}