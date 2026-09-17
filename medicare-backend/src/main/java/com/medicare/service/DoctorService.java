package com.medicare.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medicare.entity.Doctor;
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

        return doctorRepository
                .findById(id)
                .orElse(null);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {

        Doctor existingDoctor = doctorRepository
                .findById(id)
                .orElse(null);

        if (existingDoctor == null) {
            return null;
        }

        existingDoctor.setName(doctorDetails.getName());
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

    public boolean deleteDoctor(Long id) {

        if (!doctorRepository.existsById(id)) {
            return false;
        }

        doctorRepository.deleteById(id);

        return true;
    }
}