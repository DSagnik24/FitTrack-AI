package com.forgefit.forgeFit_Backend.repository;

import com.forgefit.forgeFit_Backend.entity.WorkoutSession;
import com.forgefit.forgeFit_Backend.entity.WorkoutSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {

    List<WorkoutSession> findByUser_UserId(Long userId);

    Optional<WorkoutSession> findByIdAndUser_UserId(Long id, Long userId);

    List<WorkoutSession> findByUser_UserIdAndStatus(
            Long userId,
            WorkoutSessionStatus status
    );

    List<WorkoutSession> findByUser_UserIdAndStartedAtBetween(
            Long userId,
            LocalDateTime start,
            LocalDateTime end
    );
}