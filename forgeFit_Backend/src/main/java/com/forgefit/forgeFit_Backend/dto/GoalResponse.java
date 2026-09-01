package com.forgefit.forgeFit_Backend.dto;

import com.forgefit.forgeFit_Backend.entity.GoalStatus;
import com.forgefit.forgeFit_Backend.entity.GoalType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalResponse {

    private Long id;

    private Long userId;

    private GoalType goalType;

    private GoalStatus status;

    private BigDecimal targetWeightKg;

    private LocalDate targetDate;

    private Integer targetCalories;

    private Integer targetProteinG;

    private Integer targetSteps;

    private BigDecimal targetWaterLiters;
}