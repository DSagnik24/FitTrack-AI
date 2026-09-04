package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.WorkoutSessionRequest;
import com.forgefit.forgeFit_Backend.dto.WorkoutSessionResponse;
import com.forgefit.forgeFit_Backend.service.WorkoutSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-sessions")
@RequiredArgsConstructor
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    @PostMapping
    public ResponseEntity<WorkoutSessionResponse> startWorkoutSession(
            Authentication authentication,
            @RequestBody WorkoutSessionRequest request
            ) {
        return ResponseEntity.ok(
                workoutSessionService.startWorkoutSession(
                        authentication.getName(),
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<WorkoutSessionResponse>> getAllSessions(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                workoutSessionService.getAllSessions(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<WorkoutSessionResponse> getSession(
            @PathVariable Long sessionId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                workoutSessionService.getSession(
                        authentication.getName(),
                        sessionId
                )
        );
    }

    @PatchMapping("/{sessionId}/complete")
    public ResponseEntity<WorkoutSessionResponse> completeWorkoutSession(
            @PathVariable Long sessionId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                workoutSessionService.completeWorkoutSession(
                        authentication.getName(),
                        sessionId
                )
        );
    }

    @PatchMapping("/{sessionId}/cancel")
    public ResponseEntity<WorkoutSessionResponse> cancelWorkoutSession(
            @PathVariable Long sessionId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                workoutSessionService.cancelWorkoutSession(
                        authentication.getName(),
                        sessionId
                )
        );
    }
}