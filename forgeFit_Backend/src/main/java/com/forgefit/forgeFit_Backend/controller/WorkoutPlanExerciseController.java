package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.WorkoutPlanExerciseRequest;
import com.forgefit.forgeFit_Backend.dto.WorkoutPlanExerciseResponse;
import com.forgefit.forgeFit_Backend.service.WorkoutPlanExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-plans")
@RequiredArgsConstructor
public class WorkoutPlanExerciseController {

    private final WorkoutPlanExerciseService workoutPlanExerciseService;

    @PostMapping("/{planId}/exercises")
    public ResponseEntity<WorkoutPlanExerciseResponse> addExerciseToWorkoutPlan(
            @PathVariable Long planId,
            Authentication authentication,
            @Valid @RequestBody WorkoutPlanExerciseRequest request
    ) {
        return ResponseEntity.ok(
                workoutPlanExerciseService.addExerciseToWorkoutPlan(
                        authentication.getName(),
                        planId,
                        request
                )
        );
    }

    @GetMapping("/{planId}/exercises")
    public ResponseEntity<List<WorkoutPlanExerciseResponse>> getExercisesForWorkoutPlan(
            @PathVariable Long planId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                workoutPlanExerciseService.getExercisesForWorkoutPlan(
                        authentication.getName(),
                        planId
                )
        );
    }

    @PutMapping("/{planId}/exercises/{planExerciseId}")
    public ResponseEntity<WorkoutPlanExerciseResponse> updateWorkoutPlanExercise(
            @PathVariable Long planId,
            @PathVariable Long planExerciseId,
            Authentication authentication,
            @Valid @RequestBody WorkoutPlanExerciseRequest request
    ) {
        return ResponseEntity.ok(
                workoutPlanExerciseService.updateWorkoutPlanExercise(
                        authentication.getName(),
                        planId,
                        planExerciseId,
                        request
                )
        );
    }

    @DeleteMapping("/{planId}/exercises/{planExerciseId}")
    public ResponseEntity<Void> deleteWorkoutPlanExercise(
            @PathVariable Long planId,
            @PathVariable Long planExerciseId,
            Authentication authentication
    ) {
        workoutPlanExerciseService.deleteWorkoutPlanExercise(
                authentication.getName(),
                planId,
                planExerciseId
        );

        return ResponseEntity.noContent().build();
    }
}