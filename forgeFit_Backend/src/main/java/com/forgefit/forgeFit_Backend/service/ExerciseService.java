package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.ExerciseRequest;
import com.forgefit.forgeFit_Backend.dto.ExerciseResponse;
import com.forgefit.forgeFit_Backend.entity.Exercise;
import com.forgefit.forgeFit_Backend.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseResponse createExercise(
            ExerciseRequest request
    ) {

        if (exerciseRepository
                .findByNameIgnoreCase(request.getName())
                .isPresent()) {

            throw new RuntimeException(
                    "Exercise already exists"
            );
        }

        Exercise exercise = Exercise.builder()
                .name(request.getName())
                .description(request.getDescription())
                .muscleGroup(request.getMuscleGroup())
                .equipment(request.getEquipment())
                .difficulty(request.getDifficulty())
                .build();

        Exercise savedExercise =
                exerciseRepository.save(exercise);

        return mapToResponse(savedExercise);
    }

    public ExerciseResponse getExercise(Long id) {

        Exercise exercise =
                exerciseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Exercise not found"
                                )
                        );

        return mapToResponse(exercise);
    }

    public List<ExerciseResponse> getAllExercise(){
        return exerciseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ExerciseResponse mapToResponse(
            Exercise exercise
    ) {

        return ExerciseResponse.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .description(exercise.getDescription())
                .muscleGroup(exercise.getMuscleGroup())
                .equipment(exercise.getEquipment())
                .difficulty(exercise.getDifficulty())
                .build();
    }
}