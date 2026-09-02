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
public class NutritionLogRequest {

    @NotNull
    private LocalDate date;

    @PositiveOrZero
    private Integer calories;

    @PositiveOrZero
    private BigDecimal proteinG;

    @PositiveOrZero
    private BigDecimal carbohydratesG;

    @PositiveOrZero
    private BigDecimal fatsG;

    @PositiveOrZero
    private BigDecimal fiberG;

    @PositiveOrZero
    private BigDecimal waterLiters;

    private String notes;
}