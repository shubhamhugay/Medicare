package com.medicare.controller;

import java.math.BigDecimal;
import java.util.List;

import com.medicare.dto.DoctorPageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.dto.DoctorProfileRequest;
import com.medicare.dto.DoctorResponse;
import com.medicare.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(
            DoctorService doctorService) {

        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponse>
    createDoctor(
            @Valid
            @RequestBody
            DoctorProfileRequest request) {

        DoctorResponse savedDoctor =
                doctorService.createDoctor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedDoctor);
    }

    @GetMapping
    public ResponseEntity<DoctorPageResponse>
    searchDoctors(

            @RequestParam(required = false)
            String specialization,

            @RequestParam(required = false)
            BigDecimal maxFee,

            @RequestParam(required = false)
            String name,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "6")
            int size,

            @RequestParam(
                    defaultValue = "consultationFee"
            )
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction) {

        DoctorPageResponse response =
                doctorService.searchDoctors(
                        specialization,
                        maxFee,
                        name,
                        page,
                        size,
                        sortBy,
                        direction
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse>
    getDoctorById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                doctorService.getDoctorById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse>
    updateDoctor(
            @PathVariable Long id,
            @Valid
            @RequestBody
            DoctorProfileRequest request) {

        return ResponseEntity.ok(
                doctorService.updateDoctor(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteDoctor(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity
                .noContent()
                .build();
    }


    @PostMapping("/profile")
    public ResponseEntity<DoctorResponse>
    createMyProfile(
            @Valid
            @RequestBody
            DoctorProfileRequest request,
            Authentication authentication) {

        DoctorResponse response =
                doctorService.createMyProfile(
                        request,
                        authentication.getName()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/profile/me")
    public ResponseEntity<DoctorResponse>
    getMyProfile(
            Authentication authentication) {

        return ResponseEntity.ok(
                doctorService.getMyProfile(
                        authentication.getName()
                )
        );
    }

    @PutMapping("/profile/me")
    public ResponseEntity<DoctorResponse>
    updateMyProfile(
            @Valid
            @RequestBody
            DoctorProfileRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(
                doctorService.updateMyProfile(
                        authentication.getName(),
                        request
                )
        );
    }

    @DeleteMapping("/profile/me")
    public ResponseEntity<Void>
    deleteMyProfile(
            Authentication authentication) {

        doctorService.deleteMyProfile(
                authentication.getName()
        );

        return ResponseEntity
                .noContent()
                .build();
    }



}