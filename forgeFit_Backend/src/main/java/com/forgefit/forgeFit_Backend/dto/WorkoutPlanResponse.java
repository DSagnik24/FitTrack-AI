package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutPlanResponse {

    private Long id;

    private Long userId;

    private String name;

    private String description;

    private LocalDateTime createdAt;
}