package com.forgefit.forgeFit_Backend.repository;

import com.forgefit.forgeFit_Backend.entity.WeightLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WeightLogRepository
        extends JpaRepository<WeightLog, Long> {

    List<WeightLog> findByUser_UserIdOrderByDateDesc(
            Long userId
    );

    Optional<WeightLog> findByIdAndUser_UserId(
            Long id,
            Long userId
    );
}