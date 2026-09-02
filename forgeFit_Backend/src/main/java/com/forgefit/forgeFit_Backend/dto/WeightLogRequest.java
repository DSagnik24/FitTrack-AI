package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeightLogRequest {

    @NotNull
    @Positive
    private BigDecimal weightKg;

    @NotNull
    private LocalDate date;

    private String notes;
}