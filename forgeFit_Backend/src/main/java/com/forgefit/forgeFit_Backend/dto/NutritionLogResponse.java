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
public class NutritionLogResponse {

    private Long id;

    private Long userId;

    private LocalDate date;

    private Integer calories;

    private BigDecimal proteinG;

    private BigDecimal carbohydratesG;

    private BigDecimal fatsG;

    private BigDecimal fiberG;

    private BigDecimal waterLiters;

    private String notes;

    private LocalDateTime createdAt;
}