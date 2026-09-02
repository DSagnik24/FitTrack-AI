package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.WorkoutSessionExerciseRequest;
import com.forgefit.forgeFit_Backend.dto.WorkoutSessionExerciseResponse;
import com.forgefit.forgeFit_Backend.entity.Exercise;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.entity.WorkoutSession;
import com.forgefit.forgeFit_Backend.entity.WorkoutSessionExercise;
import com.forgefit.forgeFit_Backend.repository.ExerciseRepository;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutSessionExerciseRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutSessionExerciseService {

    private final WorkoutSessionExerciseRepository workoutSessionExerciseRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public WorkoutSessionExerciseResponse addExerciseToSession(
            String email,
            Long sessionId,
            WorkoutSessionExerciseRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WorkoutSession session =
                workoutSessionRepository
                        .findByIdAndUser_UserId(
                                sessionId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Workout session not found"
                                )
                        );

        Exercise exercise = exerciseRepository
                .findById(request.getExerciseId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exercise not found"
                        )
                );

        WorkoutSessionExercise sessionExercise =
                WorkoutSessionExercise.builder()
                        .workoutSession(session)
                        .exercise(exercise)
                        .setsCompleted(request.getSetsCompleted())
                        .repsCompleted(request.getRepsCompleted())
                        .weightKg(request.getWeightKg())
                        .durationSeconds(request.getDurationSeconds())
                        .notes(request.getNotes())
                        .exerciseOrder(request.getExerciseOrder())
                        .build();

        WorkoutSessionExercise saved =
                workoutSessionExerciseRepository.save(
                        sessionExercise
                );

        return mapToResponse(saved);
    }

    public List<WorkoutSessionExerciseResponse> getExercisesForSession(
            String email,
            Long sessionId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        workoutSessionRepository
                .findByIdAndUser_UserId(
                        sessionId,
                        user.getUserId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Workout session not found"
                        )
                );

        return workoutSessionExerciseRepository
                .findByWorkoutSession_Id(sessionId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public WorkoutSessionExerciseResponse updateExerciseInSession(
            String email,
            Long sessionId,
            Long sessionExerciseId,
            WorkoutSessionExerciseRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        workoutSessionRepository
                .findByIdAndUser_UserId(
                        sessionId,
                        user.getUserId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Workout session not found"
                        )
                );

        WorkoutSessionExercise sessionExercise =
                workoutSessionExerciseRepository
                        .findByIdAndWorkoutSession_Id(
                                sessionExerciseId,
                                sessionId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Workout session exercise not found"
                                )
                        );

        Exercise exercise = exerciseRepository
                .findById(request.getExerciseId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exercise not found"
                        )
                );

        sessionExercise.setExercise(exercise);
        sessionExercise.setSetsCompleted(
                request.getSetsCompleted()
        );
        sessionExercise.setRepsCompleted(
                request.getRepsCompleted()
        );
        sessionExercise.setWeightKg(
                request.getWeightKg()
        );
        sessionExercise.setDurationSeconds(
                request.getDurationSeconds()
        );
        sessionExercise.setNotes(
                request.getNotes()
        );
        sessionExercise.setExerciseOrder(
                request.getExerciseOrder()
        );

        WorkoutSessionExercise updated =
                workoutSessionExerciseRepository.save(
                        sessionExercise
                );

        return mapToResponse(updated);
    }

    public void deleteExerciseFromSession(
            String email,
            Long sessionId,
            Long sessionExerciseId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        workoutSessionRepository
                .findByIdAndUser_UserId(
                        sessionId,
                        user.getUserId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Workout session not found"
                        )
                );

        WorkoutSessionExercise sessionExercise =
                workoutSessionExerciseRepository
                        .findByIdAndWorkoutSession_Id(
                                sessionExerciseId,
                                sessionId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Workout session exercise not found"
                                )
                        );

        workoutSessionExerciseRepository.delete(
                sessionExercise
        );
    }

    private WorkoutSessionExerciseResponse mapToResponse(
            WorkoutSessionExercise sessionExercise
    ) {

        return WorkoutSessionExerciseResponse.builder()
                .id(sessionExercise.getId())
                .workoutSessionId(
                        sessionExercise
                                .getWorkoutSession()
                                .getId()
                )
                .exerciseId(
                        sessionExercise
                                .getExercise()
                                .getId()
                )
                .exerciseName(
                        sessionExercise
                                .getExercise()
                                .getName()
                )
                .setsCompleted(
                        sessionExercise.getSetsCompleted()
                )
                .repsCompleted(
                        sessionExercise.getRepsCompleted()
                )
                .weightKg(
                        sessionExercise.getWeightKg()
                )
                .durationSeconds(
                        sessionExercise.getDurationSeconds()
                )
                .notes(
                        sessionExercise.getNotes()
                )
                .exerciseOrder(
                        sessionExercise.getExerciseOrder()
                )
                .build();
    }
}