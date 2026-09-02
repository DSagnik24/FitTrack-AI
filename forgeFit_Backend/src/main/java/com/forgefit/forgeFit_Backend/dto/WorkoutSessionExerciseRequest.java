package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSessionExerciseRequest {

    @NotNull
    private Long exerciseId;

    @NotNull
    private Integer setsCompleted;

    @NotNull
    private Integer repsCompleted;

    private Double weightKg;

    private Integer durationSeconds;

    private String notes;

    private Integer exerciseOrder;
}