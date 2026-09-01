package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.WorkoutPlanRequest;
import com.forgefit.forgeFit_Backend.dto.WorkoutPlanResponse;
import com.forgefit.forgeFit_Backend.service.WorkoutPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-plans")
@RequiredArgsConstructor
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    @PostMapping
    public ResponseEntity<WorkoutPlanResponse> createWorkoutPlan(
            Authentication authentication,
            @Valid @RequestBody WorkoutPlanRequest request
    ) {

        WorkoutPlanResponse workoutPlan =
                workoutPlanService.createWorkoutPlan(
                        authentication.getName(),
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workoutPlan);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutPlanResponse>> getAllWorkoutPlans(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                workoutPlanService.getAllWorkoutPlans(
                        authentication.getName()
                )
        );
    }
}