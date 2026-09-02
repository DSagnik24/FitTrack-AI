package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private BigDecimal currentWeight;
    private BigDecimal targetWeight;

    private String goalType;

    private Integer todaySteps;
    private Integer targetSteps;

    private Integer todayCalories;
    private Integer targetCalories;

    private BigDecimal todayProtein;
    private Integer targetProtein;

    private BigDecimal todayWater;
    private BigDecimal targetWater;

    private long weeklyWorkouts;
    private long weeklyWorkoutMinutes;
}