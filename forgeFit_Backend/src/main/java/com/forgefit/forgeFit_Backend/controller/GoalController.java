package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.GoalRequest;
import com.forgefit.forgeFit_Backend.dto.GoalResponse;
import com.forgefit.forgeFit_Backend.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import com.forgefit.forgeFit_Backend.entity.GoalStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalResponse> createGoal(
            Authentication authentication,
            @Valid @RequestBody GoalRequest request
            ){
        GoalResponse goal =
                goalService.createGoal(
                        authentication.getName(), request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(goal);
    }
    @GetMapping
    public ResponseEntity<GoalResponse> getGoal(
            Authentication authentication
    ){
        return ResponseEntity.ok(
                goalService.getGoal(
                        authentication.getName()
                )
        );
    }
    @PutMapping
    public ResponseEntity<GoalResponse> updateGoal(
            Authentication authentication,
            @Valid @RequestBody GoalRequest request
    ){
        GoalResponse goal = goalService.updateGoal(
                authentication.getName(), request
        );
        return ResponseEntity.ok(goal);
    }
    @PatchMapping("/status")
    public ResponseEntity<GoalResponse> updateGoalStatus(
            Authentication authentication,
            @RequestParam GoalStatus status
    ) {

        GoalResponse goal =
                goalService.updateGoalStatus(
                        authentication.getName(),
                        status
                );

        return ResponseEntity.ok(goal);
    }

}
