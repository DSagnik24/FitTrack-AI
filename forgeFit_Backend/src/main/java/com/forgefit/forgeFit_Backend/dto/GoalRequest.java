package com.forgefit.forgeFit_Backend.dto;

import com.forgefit.forgeFit_Backend.entity.GoalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalRequest {
    @NotNull
    private GoalType goalType;

    private BigDecimal targetWeightKg;

    private LocalDate targetDate;

    private Integer targetCalories;

    private Integer targetProteinG;

    private Integer targetSteps;

    private BigDecimal targetWaterLiters;
}
