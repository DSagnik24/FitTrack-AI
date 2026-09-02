package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutHistoryStatsResponse {

    private long totalWorkouts;

    private long completedWorkouts;

    private long cancelledWorkouts;

    private long totalWorkoutMinutes;

    private double averageWorkoutMinutes;
}