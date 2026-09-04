package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIWorkoutPlanExerciseResponse {

    private Long exerciseId;
    private String exerciseName;

    private Integer dayNumber;

    private Integer sets;
    private Integer reps;

    private Double weightKg;
    private Integer restSeconds;

    private Integer exerciseOrder;
}