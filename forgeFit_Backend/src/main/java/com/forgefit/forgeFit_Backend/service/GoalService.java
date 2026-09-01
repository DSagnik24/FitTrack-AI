package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.GoalRequest;
import com.forgefit.forgeFit_Backend.dto.GoalResponse;
import com.forgefit.forgeFit_Backend.entity.Goal;
import com.forgefit.forgeFit_Backend.entity.GoalStatus;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.repository.GoalRepository;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public GoalResponse createGoal(
            String email,
            GoalRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Goal goal = Goal.builder()
                .user(user)
                .goalType(request.getGoalType())
                .status(GoalStatus.ACTIVE)
                .targetWeightKg(request.getTargetWeightKg())
                .targetDate(request.getTargetDate())
                .targetCalories(request.getTargetCalories())
                .targetProteinG(request.getTargetProteinG())
                .targetSteps(request.getTargetSteps())
                .targetWaterLiters(request.getTargetWaterLiters())
                .build();

        Goal savedGoal = goalRepository.save(goal);

        return mapToResponse(savedGoal);
    }

    public GoalResponse getGoal(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Goal goal = goalRepository
                .findByUser_UserId(user.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Goal not found")
                );

        return mapToResponse(goal);
    }

    public GoalResponse updateGoal(
            String email,
            GoalRequest request
    ){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new RuntimeException("User not found"));

        Goal goal = goalRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(()->new RuntimeException("Goal not found"));

        goal.setGoalType(request.getGoalType());
        goal.setTargetWeightKg(request.getTargetWeightKg());
        goal.setTargetDate(request.getTargetDate());
        goal.setTargetCalories(request.getTargetCalories());
        goal.setTargetProteinG(request.getTargetProteinG());
        goal.setTargetSteps(request.getTargetSteps());
        goal.setTargetWaterLiters(request.getTargetWaterLiters());

        Goal updatedGoal = goalRepository.save(goal);
        return mapToResponse(updatedGoal);
    }

    public GoalResponse updateGoalStatus(
            String email,
            GoalStatus status
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Goal goal = goalRepository
                .findByUser_UserId(user.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Goal not found")
                );

        goal.setStatus(status);

        Goal updatedGoal = goalRepository.save(goal);

        return mapToResponse(updatedGoal);
    }

    private GoalResponse mapToResponse(Goal goal) {

        return GoalResponse.builder()
                .id(goal.getId())
                .userId(goal.getUser().getUserId())
                .goalType(goal.getGoalType())
                .status(goal.getStatus())
                .targetWeightKg(goal.getTargetWeightKg())
                .targetDate(goal.getTargetDate())
                .targetCalories(goal.getTargetCalories())
                .targetProteinG(goal.getTargetProteinG())
                .targetSteps(goal.getTargetSteps())
                .targetWaterLiters(goal.getTargetWaterLiters())
                .build();
    }
}