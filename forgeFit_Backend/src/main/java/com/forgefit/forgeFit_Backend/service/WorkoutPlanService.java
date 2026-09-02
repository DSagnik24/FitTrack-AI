package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.WorkoutPlanRequest;
import com.forgefit.forgeFit_Backend.dto.WorkoutPlanResponse;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.entity.WorkoutPlan;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;

    public WorkoutPlanResponse createWorkoutPlan(
            String email,
            WorkoutPlanRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WorkoutPlan workoutPlan = WorkoutPlan.builder()
                .user(user)
                .name(request.getName())
                .description(request.getDescription())
                .build();

        WorkoutPlan savedPlan =
                workoutPlanRepository.save(workoutPlan);

        return mapToResponse(savedPlan);
    }

    public List<WorkoutPlanResponse> getAllWorkoutPlans(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return workoutPlanRepository
                .findByUser_UserId(user.getUserId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public WorkoutPlanResponse updateWorkoutPlan(
            String email,
            Long planId,
            WorkoutPlanRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WorkoutPlan workoutPlan =
                workoutPlanRepository.findById(planId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Workout plan not found"
                                )
                        );

        if (!workoutPlan.getUser().getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "You are not authorized to update this workout plan"
            );
        }

        workoutPlan.setName(request.getName());
        workoutPlan.setDescription(request.getDescription());

        WorkoutPlan updatedPlan =
                workoutPlanRepository.save(workoutPlan);

        return mapToResponse(updatedPlan);
    }

    public void deleteWorkoutPlan(
            String email,
            Long planId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WorkoutPlan workoutPlan =
                workoutPlanRepository.findById(planId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Workout plan not found"
                                )
                        );

        if (!workoutPlan.getUser().getUserId()
                .equals(user.getUserId())) {

            throw new RuntimeException(
                    "You are not authorized to delete this workout plan"
            );
        }

        workoutPlanRepository.delete(workoutPlan);
    }

    private WorkoutPlanResponse mapToResponse(
            WorkoutPlan workoutPlan
    ) {

        return WorkoutPlanResponse.builder()
                .id(workoutPlan.getId())
                .userId(workoutPlan.getUser().getUserId())
                .name(workoutPlan.getName())
                .description(workoutPlan.getDescription())
                .createdAt(workoutPlan.getCreatedAt())
                .build();
    }
}