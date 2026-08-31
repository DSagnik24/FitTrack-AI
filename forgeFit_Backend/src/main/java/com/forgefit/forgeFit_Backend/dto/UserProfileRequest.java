package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRequest {

    private String gender;

    private LocalDate dateOfBirth;

    @DecimalMin(value = "50.0", message = "Height must be at least 50 cm")
    private BigDecimal heightCm;

    @DecimalMin(value = "20.0", message = "Weight must be at least 20 kg")
    private BigDecimal currentWeightKg;

    private String activityLevel;
}