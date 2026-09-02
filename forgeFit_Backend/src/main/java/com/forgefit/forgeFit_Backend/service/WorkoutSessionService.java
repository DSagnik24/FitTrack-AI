package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.WorkoutSessionResponse;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.entity.WorkoutPlan;
import com.forgefit.forgeFit_Backend.entity.WorkoutSession;
import com.forgefit.forgeFit_Backend.entity.WorkoutSessionStatus;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
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

    public WorkoutSessionResponse startWorkoutSession(
            String email,
            Long planId,
            String notes
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WorkoutPlan workoutPlan = null;

        if (planId != null) {

            workoutPlan = workoutPlanRepository.findById(planId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Workout plan not found"
                            )
                    );

            if (!workoutPlan.getUser().getUserId()
                    .equals(user.getUserId())) {

                throw new RuntimeException(
                        "You are not authorized to use this workout plan"
                );
            }
        }

        WorkoutSession session = WorkoutSession.builder()
                .user(user)
                .workoutPlan(workoutPlan)
                .startedAt(LocalDateTime.now())
                .status(WorkoutSessionStatus.IN_PROGRESS)
                .notes(notes)
                .build();

        WorkoutSession saved =
                workoutSessionRepository.save(session);

        return mapToResponse(saved);
    }

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

    public WorkoutSessionResponse completeWorkoutSession(
            String email,
            Long sessionId
    ) {

        WorkoutSession session =
                getSessionEntity(email, sessionId);

        session.setStatus(
                WorkoutSessionStatus.COMPLETED
        );

        session.setCompletedAt(
                LocalDateTime.now()
        );

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

    private WorkoutSessionResponse mapToResponse(
            WorkoutSession session
    ) {

        return WorkoutSessionResponse.builder()
                .id(session.getId())
                .userId(session.getUser().getUserId())
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
                .build();
    }
}