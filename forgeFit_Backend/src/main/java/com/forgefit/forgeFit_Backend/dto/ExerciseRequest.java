package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseRequest {

    @NotBlank
    private String name;

    private String description;

    private String muscleGroup;

    private String equipment;

    private String difficulty;
}