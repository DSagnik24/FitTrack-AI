package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Long id;

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;

    private String gender;
    private LocalDate dateOfBirth;
    private BigDecimal heightCm;
    private BigDecimal currentWeightKg;
    private String activityLevel;
}
