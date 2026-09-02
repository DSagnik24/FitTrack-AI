package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyActivityRequest {

    @NotNull
    private LocalDate date;

    @PositiveOrZero
    private Integer steps;

    @PositiveOrZero
    private Integer caloriesBurned;

    @PositiveOrZero
    private Integer activeMinutes;

    @PositiveOrZero
    private Integer workoutMinutes;

    @PositiveOrZero
    private BigDecimal waterLiters;
}