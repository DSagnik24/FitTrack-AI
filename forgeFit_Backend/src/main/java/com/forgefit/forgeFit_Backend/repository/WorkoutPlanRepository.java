package com.forgefit.forgeFit_Backend.repository;

import com.forgefit.forgeFit_Backend.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByUser_UserId(Long userId);
}
