package com.forgefit.forgeFit_Backend.dto;

import com.forgefit.forgeFit_Backend.entity.WorkoutSessionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSessionResponse {

    private Long id;

    private Long userId;

    private Long workoutPlanId;

    private String workoutPlanName;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private WorkoutSessionStatus status;

    private Integer durationMinutes;

    private Integer dayNumber;

    private String notes;
}