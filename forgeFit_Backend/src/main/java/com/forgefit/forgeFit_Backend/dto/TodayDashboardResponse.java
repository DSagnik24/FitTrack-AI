package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodayDashboardResponse {

    private String date;

    private Integer steps;
    private Integer targetSteps;

    private Integer calories;
    private Integer targetCalories;

    private BigDecimal protein;
    private Integer targetProtein;

    private BigDecimal water;
    private BigDecimal targetWater;

    private Integer activeMinutes;
    private Integer workoutMinutes;
}