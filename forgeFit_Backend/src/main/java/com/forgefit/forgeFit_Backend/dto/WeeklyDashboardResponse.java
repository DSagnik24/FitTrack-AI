package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyDashboardResponse {

    private String startDate;
    private String endDate;

    private long totalWorkouts;
    private long completedWorkouts;
    private long totalWorkoutMinutes;

    private long totalSteps;
    private long totalActiveMinutes;
}