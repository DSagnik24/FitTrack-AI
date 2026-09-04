package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSessionExerciseRequest {

    @NotNull
    private Long exerciseId;

    private Integer setsCompleted;

    private Integer repsCompleted;

    private Double weightKg;

    private Integer durationSeconds;

    private String notes;

    private Integer exerciseOrder;
}