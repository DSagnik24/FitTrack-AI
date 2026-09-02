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
public class WeightLogResponse {

    private Long id;

    private Long userId;

    private BigDecimal weightKg;

    private LocalDate date;

    private String notes;

    private LocalDateTime createdAt;
}