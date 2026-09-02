package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutPlanExerciseResponse {

    private Long id;

    private Long workoutPlanId;

    private Long exerciseId;

    private String exerciseName;

    private Integer sets;

    private Integer reps;

    private Double weightKg;

    private Integer restSeconds;

    private Integer exerciseOrder;
}