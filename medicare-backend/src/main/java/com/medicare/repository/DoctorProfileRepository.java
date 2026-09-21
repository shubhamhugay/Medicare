package com.medicare.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicare.entity.DoctorProfile;

public interface DoctorProfileRepository
        extends JpaRepository<DoctorProfile, Long> {

    boolean existsByUserId(Long userId);

    @Query("""
            SELECT d
            FROM DoctorProfile d
            JOIN d.user u
            WHERE
                (:specialization IS NULL
                    OR LOWER(d.specialization) = LOWER(:specialization))
            AND
                (:maxFee IS NULL
                    OR d.consultationFee <= :maxFee)
            AND
                (:name IS NULL
                    OR LOWER(u.name)
                    LIKE CONCAT(CONCAT('%', LOWER(:name)), '%'))
            """)
    Page<DoctorProfile> searchDoctors(
            @Param("specialization")
            String specialization,

            @Param("maxFee")
            BigDecimal maxFee,

            @Param("name")
            String name,

            Pageable pageable
    );
}