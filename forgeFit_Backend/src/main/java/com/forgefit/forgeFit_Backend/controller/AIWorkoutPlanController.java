package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.AIWorkoutPlanRequest;
import com.forgefit.forgeFit_Backend.dto.AIWorkoutPlanResponse;
import com.forgefit.forgeFit_Backend.service.AIWorkoutPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIWorkoutPlanController {

    private final AIWorkoutPlanService aiWorkoutPlanService;

    @PostMapping("/workout-plan")
    public ResponseEntity<AIWorkoutPlanResponse> generateWorkoutPlan(
            @Valid @RequestBody AIWorkoutPlanRequest request,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                aiWorkoutPlanService.generateWorkoutPlan(
                        authentication.getName(),
                        request
                )
        );
    }
}