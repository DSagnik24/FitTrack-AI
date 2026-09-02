package com.forgefit.forgeFit_Backend.repository;

import com.forgefit.forgeFit_Backend.entity.WorkoutSessionExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutSessionExerciseRepository
        extends JpaRepository<WorkoutSessionExercise, Long> {

    List<WorkoutSessionExercise> findByWorkoutSession_Id(
            Long workoutSessionId
    );

    Optional<WorkoutSessionExercise> findByIdAndWorkoutSession_Id(
            Long id,
            Long workoutSessionId
    );
}