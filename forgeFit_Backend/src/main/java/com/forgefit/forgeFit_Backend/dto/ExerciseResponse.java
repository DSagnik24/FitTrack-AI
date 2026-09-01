package com.forgefit.forgeFit_Backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseResponse {

    private Long id;

    private String name;

    private String description;

    private String muscleGroup;

    private String equipment;

    private String difficulty;
}