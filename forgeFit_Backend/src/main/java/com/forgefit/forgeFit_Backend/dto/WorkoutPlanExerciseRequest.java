package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutPlanExerciseRequest {

    @NotNull
    private Long exerciseId;

    @NotNull
    private Integer sets;

    @NotNull
    private Integer reps;

    private Double weightKg;

    private Integer restSeconds;

    private Integer exerciseOrder;
}