package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.WorkoutHistoryExerciseResponse;
import com.forgefit.forgeFit_Backend.dto.WorkoutHistoryResponse;
import com.forgefit.forgeFit_Backend.dto.WorkoutHistoryStatsResponse;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.entity.WorkoutSession;
import com.forgefit.forgeFit_Backend.entity.WorkoutSessionExercise;
import com.forgefit.forgeFit_Backend.entity.WorkoutSessionStatus;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutSessionExerciseRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutHistoryService {

    private final UserRepository userRepository;
    private final WorkoutSessionRepository workoutSessionRepository;
    private final WorkoutSessionExerciseRepository workoutSessionExerciseRepository;


    // ---------------------------------------------------------
    // GET ALL WORKOUT HISTORY
    // ---------------------------------------------------------

    public List<WorkoutHistoryResponse> getWorkoutHistory(String email) {

        User user = getUser(email);

        return workoutSessionRepository
                .findByUser_UserId(user.getUserId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ---------------------------------------------------------
    // GET COMPLETED WORKOUTS
    // ---------------------------------------------------------

    public List<WorkoutHistoryResponse> getCompletedWorkouts(String email) {

        User user = getUser(email);

        return workoutSessionRepository
                .findByUser_UserIdAndStatus(
                        user.getUserId(),
                        WorkoutSessionStatus.COMPLETED
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ---------------------------------------------------------
    // GET SINGLE WORKOUT
    // ---------------------------------------------------------

    public WorkoutHistoryResponse getWorkoutHistoryById(
            String email,
            Long sessionId
    ) {

        User user = getUser(email);

        WorkoutSession session = workoutSessionRepository
                .findByIdAndUser_UserId(sessionId, user.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Workout session not found")
                );

        return mapToResponse(session);
    }


    // ---------------------------------------------------------
    // GET WORKOUTS BY DATE RANGE
    // ---------------------------------------------------------

    public List<WorkoutHistoryResponse> getWorkoutHistoryByDateRange(
            String email,
            LocalDate startDate,
            LocalDate endDate
    ) {

        User user = getUser(email);

        LocalDateTime startDateTime =
                startDate.atStartOfDay();

        LocalDateTime endDateTime =
                endDate.plusDays(1).atStartOfDay().minusNanos(1);

        return workoutSessionRepository
                .findByUser_UserIdAndStartedAtBetween(
                        user.getUserId(),
                        startDateTime,
                        endDateTime
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // ---------------------------------------------------------
    // WORKOUT STATISTICS
    // ---------------------------------------------------------

    public WorkoutHistoryStatsResponse getWorkoutStats(String email) {

        User user = getUser(email);

        List<WorkoutSession> sessions =
                workoutSessionRepository
                        .findByUser_UserId(user.getUserId());

        long totalWorkouts = sessions.size();

        long completedWorkouts = sessions.stream()
                .filter(session ->
                        session.getStatus() ==
                                WorkoutSessionStatus.COMPLETED
                )
                .count();

        long cancelledWorkouts = sessions.stream()
                .filter(session ->
                        session.getStatus() ==
                                WorkoutSessionStatus.CANCELLED
                )
                .count();

        long totalWorkoutMinutes = sessions.stream()
                .filter(session ->
                        session.getStatus() ==
                                WorkoutSessionStatus.COMPLETED
                )
                .filter(session ->
                        session.getDurationMinutes() != null
                )
                .mapToLong(WorkoutSession::getDurationMinutes)
                .sum();

        double averageWorkoutMinutes =
                completedWorkouts == 0
                        ? 0
                        : (double) totalWorkoutMinutes
                        / completedWorkouts;

        return WorkoutHistoryStatsResponse.builder()
                .totalWorkouts(totalWorkouts)
                .completedWorkouts(completedWorkouts)
                .cancelledWorkouts(cancelledWorkouts)
                .totalWorkoutMinutes(totalWorkoutMinutes)
                .averageWorkoutMinutes(
                        Math.round(averageWorkoutMinutes * 100.0) / 100.0
                )
                .build();
    }


    // ---------------------------------------------------------
    // MAP SESSION → RESPONSE
    // ---------------------------------------------------------

    private WorkoutHistoryResponse mapToResponse(
            WorkoutSession session
    ) {

        List<WorkoutSessionExercise> exercises =
                workoutSessionExerciseRepository
                        .findByWorkoutSession_Id(session.getId());

        List<WorkoutHistoryExerciseResponse> exerciseResponses =
                exercises.stream()
                        .map(this::mapExerciseToResponse)
                        .toList();

        return WorkoutHistoryResponse.builder()
                .sessionId(session.getId())
                .workoutPlanId(
                        session.getWorkoutPlan() != null
                                ? session.getWorkoutPlan().getId()
                                : null
                )
                .workoutPlanName(
                        session.getWorkoutPlan() != null
                                ? session.getWorkoutPlan().getName()
                                : null
                )
                .startedAt(session.getStartedAt())
                .completedAt(session.getCompletedAt())
                .status(session.getStatus())
                .durationMinutes(session.getDurationMinutes())
                .notes(session.getNotes())
                .exercises(exerciseResponses)
                .build();
    }


    // ---------------------------------------------------------
    // MAP EXERCISE → RESPONSE
    // ---------------------------------------------------------

    private WorkoutHistoryExerciseResponse mapExerciseToResponse(
            WorkoutSessionExercise exercise
    ) {

        return WorkoutHistoryExerciseResponse.builder()
                .sessionExerciseId(exercise.getId())
                .exerciseId(exercise.getExercise().getId())
                .exerciseName(exercise.getExercise().getName())
                .setsCompleted(exercise.getSetsCompleted())
                .repsCompleted(exercise.getRepsCompleted())
                .weightKg(exercise.getWeightKg())
                .durationSeconds(exercise.getDurationSeconds())
                .notes(exercise.getNotes())
                .exerciseOrder(exercise.getExerciseOrder())
                .build();
    }


    // ---------------------------------------------------------
    // GET USER
    // ---------------------------------------------------------

    private User getUser(String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }
}