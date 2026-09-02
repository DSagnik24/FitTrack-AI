package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.WorkoutHistoryResponse;
import com.forgefit.forgeFit_Backend.dto.WorkoutHistoryStatsResponse;
import com.forgefit.forgeFit_Backend.service.WorkoutHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workout-history")
@RequiredArgsConstructor
public class WorkoutHistoryController {

    private final WorkoutHistoryService workoutHistoryService;


    // ---------------------------------------------------------
    // GET ALL HISTORY
    // ---------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<WorkoutHistoryResponse>> getWorkoutHistory(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                workoutHistoryService.getWorkoutHistory(email)
        );
    }


    // ---------------------------------------------------------
    // GET COMPLETED WORKOUTS
    // ---------------------------------------------------------

    @GetMapping("/completed")
    public ResponseEntity<List<WorkoutHistoryResponse>> getCompletedWorkouts(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                workoutHistoryService.getCompletedWorkouts(email)
        );
    }


    // ---------------------------------------------------------
    // GET WORKOUT BY ID
    // ---------------------------------------------------------

    @GetMapping("/{sessionId}")
    public ResponseEntity<WorkoutHistoryResponse> getWorkoutHistoryById(
            @PathVariable Long sessionId,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                workoutHistoryService.getWorkoutHistoryById(
                        email,
                        sessionId
                )
        );
    }


    // ---------------------------------------------------------
    // GET BY DATE RANGE
    // ---------------------------------------------------------

    @GetMapping("/date-range")
    public ResponseEntity<List<WorkoutHistoryResponse>> getWorkoutHistoryByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                workoutHistoryService.getWorkoutHistoryByDateRange(
                        email,
                        startDate,
                        endDate
                )
        );
    }


    // ---------------------------------------------------------
    // GET STATISTICS
    // ---------------------------------------------------------

    @GetMapping("/stats")
    public ResponseEntity<WorkoutHistoryStatsResponse> getWorkoutStats(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                workoutHistoryService.getWorkoutStats(email)
        );
    }
}