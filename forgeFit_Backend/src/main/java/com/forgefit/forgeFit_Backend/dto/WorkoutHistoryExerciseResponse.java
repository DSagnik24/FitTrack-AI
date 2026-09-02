package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutHistoryExerciseResponse {

    private Long sessionExerciseId;

    private Long exerciseId;

    private String exerciseName;

    private Integer setsCompleted;

    private Integer repsCompleted;

    private Double weightKg;

    private Integer durationSeconds;

    private String notes;

    private Integer exerciseOrder;
}