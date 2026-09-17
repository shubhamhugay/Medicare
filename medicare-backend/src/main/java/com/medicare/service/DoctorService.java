package com.medicare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medicare.entity.Doctor;
import com.medicare.exception.DoctorNotFoundException;
import com.medicare.repository.DoctorRepository;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor(Doctor doctor) {

        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {

        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {

        return findDoctorById(id);
    }

    public Doctor updateDoctor(
            Long id,
            Doctor doctorDetails) {

        Doctor existingDoctor = findDoctorById(id);

        existingDoctor.setName(
                doctorDetails.getName()
        );

        existingDoctor.setSpecialization(
                doctorDetails.getSpecialization()
        );

        existingDoctor.setExperienceYears(
                doctorDetails.getExperienceYears()
        );

        existingDoctor.setConsultationFee(
                doctorDetails.getConsultationFee()
        );

        existingDoctor.setPhotoUrl(
                doctorDetails.getPhotoUrl()
        );

        return doctorRepository.save(existingDoctor);
    }

    public void deleteDoctor(Long id) {

        Doctor doctor = findDoctorById(id);

        doctorRepository.delete(doctor);
    }

    /*
     * Common method used whenever we need to find a doctor.
     * If the doctor does not exist, it throws an exception.
     */
    private Doctor findDoctorById(Long id) {

        return doctorRepository
                .findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + id
                        )
                );
    }
}