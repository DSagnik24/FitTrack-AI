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


    // =========================================================
    // LOG EXERCISE
    // =========================================================

//    @PostMapping
//    public ResponseEntity<WorkoutSessionExerciseResponse> logExercise(
//            @PathVariable Long sessionId,
//            @Valid @RequestBody WorkoutSessionExerciseRequest request,
//            Authentication authentication
//    ) {
//
//        return ResponseEntity.ok(
//                workoutSessionExerciseService.logExercise(
//                        authentication.getName(),
//                        sessionId,
//                        request
//                )
//        );
//    }


    // =========================================================
    // GET ALL EXERCISES FOR SESSION
    // =========================================================

//    @GetMapping
//    public ResponseEntity<List<WorkoutSessionExerciseResponse>>
//    getSessionExercises(
//            @PathVariable Long sessionId,
//            Authentication authentication
//    ) {
//
//        return ResponseEntity.ok(
//                workoutSessionExerciseService.getSessionExercises(
//                        authentication.getName(),
//                        sessionId
//                )
//        );
//    }


    // =========================================================
    // GET SINGLE EXERCISE LOG
    // =========================================================

    @GetMapping("/{exerciseLogId}")
    public ResponseEntity<WorkoutSessionExerciseResponse>
    getSessionExercise(
            @PathVariable Long sessionId,
            @PathVariable Long exerciseLogId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                workoutSessionExerciseService.getSessionExercise(
                        authentication.getName(),
                        sessionId,
                        exerciseLogId
                )
        );
    }


    // =========================================================
    // DELETE EXERCISE LOG
    // =========================================================

    @DeleteMapping("/{exerciseLogId}")
    public ResponseEntity<Void> deleteSessionExercise(
            @PathVariable Long sessionId,
            @PathVariable Long exerciseLogId,
            Authentication authentication
    ) {

        workoutSessionExerciseService.deleteSessionExercise(
                authentication.getName(),
                sessionId,
                exerciseLogId
        );

        return ResponseEntity.noContent().build();
    }

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