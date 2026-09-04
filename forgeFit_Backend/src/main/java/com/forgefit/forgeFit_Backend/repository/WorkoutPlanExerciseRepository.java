package com.forgefit.forgeFit_Backend.repository;

import com.forgefit.forgeFit_Backend.entity.WorkoutPlanExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutPlanExerciseRepository
        extends JpaRepository<WorkoutPlanExercise, Long> {

    List<WorkoutPlanExercise> findByWorkoutPlan_Id(Long workoutPlanId);

    List<WorkoutPlanExercise> findByWorkoutPlan_IdAndDayNumberOrderByExerciseOrder(
            Long workoutPlanId,
            Integer dayNumber
    );

}