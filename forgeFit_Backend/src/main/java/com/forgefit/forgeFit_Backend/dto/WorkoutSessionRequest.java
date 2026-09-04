package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSessionRequest {

    private Long workoutPlanId;

    private Integer dayNumber;

    private String notes;
}