package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.WorkoutPlanExerciseRequest;
import com.forgefit.forgeFit_Backend.dto.WorkoutPlanExerciseResponse;
import com.forgefit.forgeFit_Backend.entity.Exercise;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.entity.WorkoutPlan;
import com.forgefit.forgeFit_Backend.entity.WorkoutPlanExercise;
import com.forgefit.forgeFit_Backend.repository.ExerciseRepository;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutPlanExerciseRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutPlanExerciseService {

    private final WorkoutPlanExerciseRepository workoutPlanExerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public WorkoutPlanExerciseResponse addExerciseToWorkoutPlan(
            String email,
            Long planId,
            WorkoutPlanExerciseRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId)
                .orElseThrow(() ->
                        new RuntimeException("Workout plan not found")
                );

        if (!workoutPlan.getUser().getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "You are not authorized to modify this workout plan"
            );
        }

        Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() ->
                        new RuntimeException("Exercise not found")
                );

        WorkoutPlanExercise workoutPlanExercise =
                WorkoutPlanExercise.builder()
                        .workoutPlan(workoutPlan)
                        .exercise(exercise)
                        .sets(request.getSets())
                        .reps(request.getReps())
                        .weightKg(request.getWeightKg())
                        .restSeconds(request.getRestSeconds())
                        .exerciseOrder(request.getExerciseOrder())
                        .build();

        WorkoutPlanExercise saved =
                workoutPlanExerciseRepository.save(workoutPlanExercise);

        return mapToResponse(saved);
    }

    public List<WorkoutPlanExerciseResponse> getExercisesForWorkoutPlan(
            String email,
            Long planId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId)
                .orElseThrow(() ->
                        new RuntimeException("Workout plan not found")
                );

        if (!workoutPlan.getUser().getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "You are not authorized to view this workout plan"
            );
        }

        return workoutPlanExerciseRepository
                .findByWorkoutPlan_Id(planId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public WorkoutPlanExerciseResponse updateWorkoutPlanExercise(
            String email,
            Long planId,
            Long planExerciseId,
            WorkoutPlanExerciseRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId)
                .orElseThrow(() ->
                        new RuntimeException("Workout plan not found")
                );

        if (!workoutPlan.getUser().getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "You are not authorized to modify this workout plan"
            );
        }

        WorkoutPlanExercise workoutPlanExercise =
                workoutPlanExerciseRepository.findById(planExerciseId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Workout plan exercise not found"
                                )
                        );

        if (!workoutPlanExercise.getWorkoutPlan().getId()
                .equals(planId)) {

            throw new RuntimeException(
                    "Exercise does not belong to this workout plan"
            );
        }

        Exercise exercise = exerciseRepository.findById(
                request.getExerciseId()
        ).orElseThrow(() ->
                new RuntimeException("Exercise not found")
        );

        workoutPlanExercise.setExercise(exercise);
        workoutPlanExercise.setSets(request.getSets());
        workoutPlanExercise.setReps(request.getReps());
        workoutPlanExercise.setWeightKg(request.getWeightKg());
        workoutPlanExercise.setRestSeconds(request.getRestSeconds());
        workoutPlanExercise.setExerciseOrder(request.getExerciseOrder());

        WorkoutPlanExercise updated =
                workoutPlanExerciseRepository.save(workoutPlanExercise);

        return mapToResponse(updated);
    }

    public void deleteWorkoutPlanExercise(
            String email,
            Long planId,
            Long planExerciseId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WorkoutPlan workoutPlan =
                workoutPlanRepository.findById(planId)
                        .orElseThrow(() ->
                                new RuntimeException("Workout plan not found")
                        );

        if (!workoutPlan.getUser().getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "You are not authorized to modify this workout plan"
            );
        }

        WorkoutPlanExercise workoutPlanExercise =
                workoutPlanExerciseRepository.findById(planExerciseId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Workout plan exercise not found"
                                )
                        );

        if (!workoutPlanExercise.getWorkoutPlan().getId()
                .equals(planId)) {

            throw new RuntimeException(
                    "Exercise does not belong to this workout plan"
            );
        }

        workoutPlanExerciseRepository.delete(workoutPlanExercise);
    }

    private WorkoutPlanExerciseResponse mapToResponse(
            WorkoutPlanExercise workoutPlanExercise
    ) {

        return WorkoutPlanExerciseResponse.builder()
                .id(workoutPlanExercise.getId())
                .workoutPlanId(
                        workoutPlanExercise.getWorkoutPlan().getId()
                )
                .exerciseId(
                        workoutPlanExercise.getExercise().getId()
                )
                .exerciseName(
                        workoutPlanExercise.getExercise().getName()
                )
                .sets(workoutPlanExercise.getSets())
                .reps(workoutPlanExercise.getReps())
                .weightKg(workoutPlanExercise.getWeightKg())
                .restSeconds(workoutPlanExercise.getRestSeconds())
                .exerciseOrder(workoutPlanExercise.getExerciseOrder())
                .build();
    }
}