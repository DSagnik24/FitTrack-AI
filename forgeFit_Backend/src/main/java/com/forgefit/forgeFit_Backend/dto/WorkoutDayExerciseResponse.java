package com.forgefit.forgeFit_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutDayExerciseResponse {

    private Long exerciseId;

    private String exerciseName;

    private String description;

    private String muscleGroup;

    private String equipment;

    private Integer sets;

    private Integer reps;

    private Double weightKg;

    private Integer restSeconds;

    private Integer exerciseOrder;
}