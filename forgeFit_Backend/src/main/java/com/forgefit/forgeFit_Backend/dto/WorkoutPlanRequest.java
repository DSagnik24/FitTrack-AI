package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutPlanRequest {

    @NotBlank
    private String name;

    private String description;
}