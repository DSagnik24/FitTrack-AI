package com.forgefit.forgeFit_Backend.repository;

import com.forgefit.forgeFit_Backend.entity.NutritionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface NutritionLogRepository
        extends JpaRepository<NutritionLog, Long> {

    List<NutritionLog> findByUser_UserIdOrderByDateDesc(
            Long userId
    );

    Optional<NutritionLog> findByUser_UserIdAndDate(
            Long userId,
            LocalDate date
    );

    Optional<NutritionLog> findByIdAndUser_UserId(
            Long id,
            Long userId
    );
}