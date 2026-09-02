package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyActivityResponse {

    private Long id;

    private Long userId;

    private LocalDate date;

    private Integer steps;

    private Integer caloriesBurned;

    private Integer activeMinutes;

    private Integer workoutMinutes;

    private BigDecimal waterLiters;

    private LocalDateTime createdAt;
}