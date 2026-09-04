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
public class AIWorkoutPlanDayResponse {

    private Integer dayNumber;

    private String name;

    private List<AIWorkoutPlanExerciseResponse> exercises;
}