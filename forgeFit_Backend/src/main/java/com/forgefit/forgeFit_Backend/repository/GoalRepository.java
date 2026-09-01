package com.forgefit.forgeFit_Backend.repository;

import com.forgefit.forgeFit_Backend.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findByUser_UserId(Long userId);
}
