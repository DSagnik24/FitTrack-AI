package com.forgefit.forgeFit_Backend.repository;

import com.forgefit.forgeFit_Backend.entity.DailyActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyActivityRepository
        extends JpaRepository<DailyActivity, Long> {

    List<DailyActivity> findByUser_UserIdOrderByDateDesc(
            Long userId
    );

    Optional<DailyActivity> findByUser_UserIdAndDate(
            Long userId,
            LocalDate date
    );

    Optional<DailyActivity> findByIdAndUser_UserId(
            Long id,
            Long userId
    );
}