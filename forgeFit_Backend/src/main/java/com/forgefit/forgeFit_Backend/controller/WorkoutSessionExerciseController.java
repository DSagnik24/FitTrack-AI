package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.WorkoutSessionExerciseRequest;
import com.forgefit.forgeFit_Backend.dto.WorkoutSessionExerciseResponse;
import com.forgefit.forgeFit_Backend.service.WorkoutSessionExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-sessions")
@RequiredArgsConstructor
public class WorkoutSessionExerciseController {

    private final WorkoutSessionExerciseService
            workoutSessionExerciseService;

    @PostMapping("/{sessionId}/exercises")
    public ResponseEntity<WorkoutSessionExerciseResponse>
    addExerciseToSession(
            @PathVariable Long sessionId,
            Authentication authentication,
            @Valid @RequestBody WorkoutSessionExerciseRequest request
    ) {

        return ResponseEntity.ok(
                workoutSessionExerciseService.addExerciseToSession(
                        authentication.getName(),
                        sessionId,
                        request
                )
        );
    }

    @GetMapping("/{sessionId}/exercises")
    public ResponseEntity<List<WorkoutSessionExerciseResponse>>
    getExercisesForSession(
            @PathVariable Long sessionId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                workoutSessionExerciseService.getExercisesForSession(
                        authentication.getName(),
                        sessionId
                )
        );
    }

    @PutMapping("/{sessionId}/exercises/{sessionExerciseId}")
    public ResponseEntity<WorkoutSessionExerciseResponse>
    updateExerciseInSession(
            @PathVariable Long sessionId,
            @PathVariable Long sessionExerciseId,
            Authentication authentication,
            @Valid @RequestBody WorkoutSessionExerciseRequest request
    ) {

        return ResponseEntity.ok(
                workoutSessionExerciseService.updateExerciseInSession(
                        authentication.getName(),
                        sessionId,
                        sessionExerciseId,
                        request
                )
        );
    }

    @DeleteMapping("/{sessionId}/exercises/{sessionExerciseId}")
    public ResponseEntity<Void> deleteExerciseFromSession(
            @PathVariable Long sessionId,
            @PathVariable Long sessionExerciseId,
            Authentication authentication
    ) {

        workoutSessionExerciseService.deleteExerciseFromSession(
                authentication.getName(),
                sessionId,
                sessionExerciseId
        );

        return ResponseEntity.noContent().build();
    }
}