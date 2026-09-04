package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIWorkoutPlanRequest {

    private String goal;

    @Min(1)
    @Max(7)
    private Integer daysPerWeek;

    @Min(15)
    @Max(180)
    private Integer workoutDurationMinutes;

    private String additionalInstructions;
}