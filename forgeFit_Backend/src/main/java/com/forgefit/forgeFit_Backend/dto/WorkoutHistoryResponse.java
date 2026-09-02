package com.forgefit.forgeFit_Backend.dto;

import com.forgefit.forgeFit_Backend.entity.WorkoutSessionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutHistoryResponse {

    private Long sessionId;

    private Long workoutPlanId;

    private String workoutPlanName;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private WorkoutSessionStatus status;

    private Integer durationMinutes;

    private String notes;

    private List<WorkoutHistoryExerciseResponse> exercises;
}