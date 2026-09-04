package com.forgefit.forgeFit_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIWorkoutPlanResponse {

    private Long workoutPlanId;

    private String name;

    private String description;

    private List<AIWorkoutPlanDayResponse> days;
}