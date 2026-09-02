package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.entity.*;
import com.forgefit.forgeFit_Backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIContextService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final GoalRepository goalRepository;
    private final WeightLogRepository weightLogRepository;
    private final DailyActivityRepository dailyActivityRepository;
    private final NutritionLogRepository nutritionLogRepository;
    private final WorkoutSessionRepository workoutSessionRepository;

    public String buildContext(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();

        StringBuilder context = new StringBuilder();

        context.append("""
                USER PROFILE
                ============

                """);

        context.append("Name: ")
                .append(user.getFirstName())
                .append(" ")
                .append(user.getLastName())
                .append("\n");

        // Profile
        userProfileRepository.findByUser_UserId(user.getUserId())
                .ifPresent(profile -> {
                    context.append("Gender: ")
                            .append(profile.getGender())
                            .append("\n");

                    context.append("Date of Birth: ")
                            .append(profile.getDateOfBirth())
                            .append("\n");

                    context.append("Height: ")
                            .append(profile.getHeightCm())
                            .append(" cm\n");

                    context.append("Current Weight: ")
                            .append(profile.getCurrentWeightKg())
                            .append(" kg\n");

                    context.append("Activity Level: ")
                            .append(profile.getActivityLevel())
                            .append("\n");
                });

        context.append("\nGOAL\n====\n");

        // Goal
        goalRepository.findByUser_UserId(user.getUserId())
                .ifPresent(goal -> {

                    context.append("Goal Type: ")
                            .append(goal.getGoalType())
                            .append("\n");

                    context.append("Goal Status: ")
                            .append(goal.getStatus())
                            .append("\n");

                    context.append("Target Weight: ")
                            .append(goal.getTargetWeightKg())
                            .append(" kg\n");

                    context.append("Target Calories: ")
                            .append(goal.getTargetCalories())
                            .append("\n");

                    context.append("Target Protein: ")
                            .append(goal.getTargetProteinG())
                            .append(" g\n");

                    context.append("Target Steps: ")
                            .append(goal.getTargetSteps())
                            .append("\n");

                    context.append("Target Water: ")
                            .append(goal.getTargetWaterLiters())
                            .append(" L\n");
                });

        context.append("\nLATEST WEIGHT\n=============\n");

        // Latest weight
        List<WeightLog> weights =
                weightLogRepository.findByUser_UserIdOrderByDateDesc(
                        user.getUserId()
                );

        if (!weights.isEmpty()) {

            WeightLog latestWeight = weights.get(0);

            context.append("Weight: ")
                    .append(latestWeight.getWeightKg())
                    .append(" kg\n");

            context.append("Date: ")
                    .append(latestWeight.getDate())
                    .append("\n");
        }

        context.append("\nTODAY'S ACTIVITY\n================\n");

        // Today's activity
        dailyActivityRepository
                .findByUser_UserIdAndDate(user.getUserId(), today)
                .ifPresent(activity -> {

                    context.append("Steps: ")
                            .append(activity.getSteps())
                            .append("\n");

                    context.append("Calories Burned: ")
                            .append(activity.getCaloriesBurned())
                            .append("\n");

                    context.append("Active Minutes: ")
                            .append(activity.getActiveMinutes())
                            .append("\n");

                    context.append("Workout Minutes: ")
                            .append(activity.getWorkoutMinutes())
                            .append("\n");

                    context.append("Water: ")
                            .append(activity.getWaterLiters())
                            .append(" L\n");
                });

        context.append("\nTODAY'S NUTRITION\n=================\n");

        // Today's nutrition
        nutritionLogRepository
                .findByUser_UserIdAndDate(user.getUserId(), today)
                .ifPresent(nutrition -> {

                    context.append("Calories: ")
                            .append(nutrition.getCalories())
                            .append("\n");

                    context.append("Protein: ")
                            .append(nutrition.getProteinG())
                            .append(" g\n");

                    context.append("Carbohydrates: ")
                            .append(nutrition.getCarbohydratesG())
                            .append(" g\n");

                    context.append("Fats: ")
                            .append(nutrition.getFatsG())
                            .append(" g\n");

                    context.append("Fiber: ")
                            .append(nutrition.getFiberG())
                            .append(" g\n");

                    context.append("Water: ")
                            .append(nutrition.getWaterLiters())
                            .append(" L\n");
                });

        context.append("\nRECENT WORKOUTS\n===============\n");

        // Last 7 days
        LocalDateTime startOfWeek =
                today.minusDays(6).atStartOfDay();

        LocalDateTime endOfToday =
                today.atTime(LocalTime.MAX);

        List<WorkoutSession> workouts =
                workoutSessionRepository
                        .findByUser_UserIdAndStartedAtBetween(
                                user.getUserId(),
                                startOfWeek,
                                endOfToday
                        );

        context.append("Workouts in last 7 days: ")
                .append(workouts.size())
                .append("\n");

        long completedWorkouts = workouts.stream()
                .filter(workout ->
                        workout.getStatus() == WorkoutSessionStatus.COMPLETED
                )
                .count();

        context.append("Completed workouts: ")
                .append(completedWorkouts)
                .append("\n");

        return context.toString();
    }
}