package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.WorkoutSessionRequest;
import com.forgefit.forgeFit_Backend.dto.WorkoutSessionResponse;
import com.forgefit.forgeFit_Backend.entity.*;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutPlanExerciseRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutPlanRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final UserRepository userRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutPlanExerciseRepository workoutPlanExerciseRepository;


    // =========================================================
    // START WORKOUT SESSION
    // =========================================================

    public WorkoutSessionResponse startWorkoutSession(
            String email,
            WorkoutSessionRequest request
    ) {

        // 1. Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        WorkoutPlan workoutPlan = null;


        // 2. Get workout plan if provided
        if (request.getWorkoutPlanId() != null) {

            workoutPlan = workoutPlanRepository
                    .findById(request.getWorkoutPlanId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Workout plan not found"
                            )
                    );


            // 3. Check ownership
            if (!workoutPlan.getUser().getUserId()
                    .equals(user.getUserId())) {

                throw new RuntimeException(
                        "You are not authorized to use this workout plan"
                );
            }


            // 4. Validate selected day
            if (request.getDayNumber() != null) {

                List<WorkoutPlanExercise> exercises =
                        workoutPlanExerciseRepository
                                .findByWorkoutPlan_IdAndDayNumberOrderByExerciseOrder(
                                        workoutPlan.getId(),
                                        request.getDayNumber()
                                );


                if (exercises.isEmpty()) {

                    throw new RuntimeException(
                            "No exercises found for day "
                                    + request.getDayNumber()
                    );
                }
            }
        }


        // 5. Create workout session
        WorkoutSession session = WorkoutSession.builder()
                .user(user)
                .workoutPlan(workoutPlan)
                .dayNumber(request.getDayNumber())
                .startedAt(LocalDateTime.now())
                .status(WorkoutSessionStatus.IN_PROGRESS)
                .notes(request.getNotes())
                .build();


        // 6. Save session
        WorkoutSession savedSession =
                workoutSessionRepository.save(session);


        // 7. Return response
        return mapToResponse(savedSession);
    }


    // =========================================================
    // GET ALL SESSIONS
    // =========================================================

    public List<WorkoutSessionResponse> getAllSessions(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        return workoutSessionRepository
                .findByUser_UserId(user.getUserId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================================================
    // GET SINGLE SESSION
    // =========================================================

    public WorkoutSessionResponse getSession(
            String email,
            Long sessionId
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


        return mapToResponse(session);
    }


    // =========================================================
    // COMPLETE WORKOUT SESSION
    // =========================================================

    public WorkoutSessionResponse completeWorkoutSession(
            String email,
            Long sessionId
    ) {

        WorkoutSession session =
                getSessionEntity(email, sessionId);


        // Workout must still be active
        if (session.getStatus()
                != WorkoutSessionStatus.IN_PROGRESS) {

            throw new RuntimeException(
                    "Workout session is already completed or cancelled"
            );
        }


        // Mark completed
        session.setStatus(
                WorkoutSessionStatus.COMPLETED
        );


        session.setCompletedAt(
                LocalDateTime.now()
        );


        // Calculate duration
        if (session.getStartedAt() != null) {

            long minutes =
                    Duration.between(
                            session.getStartedAt(),
                            session.getCompletedAt()
                    ).toMinutes();

            session.setDurationMinutes(
                    (int) minutes
            );
        }


        WorkoutSession updated =
                workoutSessionRepository.save(session);


        return mapToResponse(updated);
    }


    // =========================================================
    // CANCEL WORKOUT SESSION
    // =========================================================

    public WorkoutSessionResponse cancelWorkoutSession(
            String email,
            Long sessionId
    ) {

        WorkoutSession session =
                getSessionEntity(email, sessionId);


        session.setStatus(
                WorkoutSessionStatus.CANCELLED
        );


        session.setCompletedAt(
                LocalDateTime.now()
        );


        WorkoutSession updated =
                workoutSessionRepository.save(session);


        return mapToResponse(updated);
    }


    // =========================================================
    // GET SESSION ENTITY
    // =========================================================

    private WorkoutSession getSessionEntity(
            String email,
            Long sessionId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        return workoutSessionRepository
                .findByIdAndUser_UserId(
                        sessionId,
                        user.getUserId()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Workout session not found"
                        )
                );
    }


    // =========================================================
    // MAP ENTITY → RESPONSE
    // =========================================================

    private WorkoutSessionResponse mapToResponse(
            WorkoutSession session
    ) {

        return WorkoutSessionResponse.builder()
                .id(session.getId())

                .userId(
                        session.getUser() != null
                                ? session.getUser().getUserId()
                                : null
                )

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

                .startedAt(
                        session.getStartedAt()
                )

                .completedAt(
                        session.getCompletedAt()
                )

                .status(
                        session.getStatus()
                )

                .durationMinutes(
                        session.getDurationMinutes()
                )

                .dayNumber(
                        session.getDayNumber()
                )

                .notes(
                        session.getNotes()
                )

                .build();
    }
}