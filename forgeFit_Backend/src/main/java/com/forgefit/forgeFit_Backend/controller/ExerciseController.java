package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.ExerciseRequest;
import com.forgefit.forgeFit_Backend.dto.ExerciseResponse;
import com.forgefit.forgeFit_Backend.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(
            @Valid @RequestBody ExerciseRequest request
    ) {

        ExerciseResponse exercise =
                exerciseService.createExercise(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(exercise);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponse> getExercise(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                exerciseService.getExercise(id)
        );
    }
    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> getAllExercise(){
        return ResponseEntity.ok(
                exerciseService.getAllExercise()
        );
    }
}