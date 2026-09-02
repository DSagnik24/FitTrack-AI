package com.forgefit.forgeFit_Backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIRequest {

    @NotBlank
    private String message;
}