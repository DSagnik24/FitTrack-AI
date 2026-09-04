package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSessionExerciseResponse {

    private Long id;

    private Long workoutSessionId;

    private Long exerciseId;

    private String exerciseName;

    private Integer setsCompleted;

    private Integer repsCompleted;

    private Double weightKg;

    private Integer durationSeconds;

    private String notes;

    private Integer exerciseOrder;
}