package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.DashboardResponse;
import com.forgefit.forgeFit_Backend.dto.TodayDashboardResponse;
import com.forgefit.forgeFit_Backend.dto.WeeklyDashboardResponse;
import com.forgefit.forgeFit_Backend.entity.*;
import com.forgefit.forgeFit_Backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final WeightLogRepository weightLogRepository;
    private final DailyActivityRepository dailyActivityRepository;
    private final NutritionLogRepository nutritionLogRepository;
    private final WorkoutSessionRepository workoutSessionRepository;


    // =========================================================
    // COMPLETE DASHBOARD
    // =========================================================

    public DashboardResponse getDashboard(String email) {

        User user = getUser(email);

        Long userId = user.getUserId();

        LocalDate today = LocalDate.now();

        // Latest weight
        BigDecimal currentWeight =
                weightLogRepository
                        .findByUser_UserIdOrderByDateDesc(userId)
                        .stream()
                        .findFirst()
                        .map(WeightLog::getWeightKg)
                        .orElse(null);

        // Goal
        Goal goal =
                goalRepository
                        .findByUser_UserId(userId)
                        .orElse(null);

        // Today's activity
        DailyActivity activity =
                dailyActivityRepository
                        .findByUser_UserIdAndDate(userId, today)
                        .orElse(null);

        // Today's nutrition
        NutritionLog nutrition =
                nutritionLogRepository
                        .findByUser_UserIdAndDate(userId, today)
                        .orElse(null);

        // Weekly workouts
        LocalDateTime weekStart =
                today.with(DayOfWeek.MONDAY).atStartOfDay();

        LocalDateTime weekEnd =
                today.plusDays(1).atStartOfDay().minusNanos(1);

        List<WorkoutSession> weeklySessions =
                workoutSessionRepository
                        .findByUser_UserIdAndStartedAtBetween(
                                userId,
                                weekStart,
                                weekEnd
                        );

        long weeklyWorkouts =
                weeklySessions.stream()
                        .filter(session ->
                                session.getStatus()
                                        == WorkoutSessionStatus.COMPLETED
                        )
                        .count();

        long weeklyWorkoutMinutes =
                weeklySessions.stream()
                        .filter(session ->
                                session.getStatus()
                                        == WorkoutSessionStatus.COMPLETED
                        )
                        .filter(session ->
                                session.getDurationMinutes() != null
                        )
                        .mapToLong(WorkoutSession::getDurationMinutes)
                        .sum();

        return DashboardResponse.builder()

                .currentWeight(currentWeight)

                .targetWeight(
                        goal != null
                                ? goal.getTargetWeightKg()
                                : null
                )

                .goalType(
                        goal != null && goal.getGoalType() != null
                                ? goal.getGoalType().name()
                                : null
                )

                .todaySteps(
                        activity != null
                                ? activity.getSteps()
                                : 0
                )

                .targetSteps(
                        goal != null
                                ? goal.getTargetSteps()
                                : null
                )

                .todayCalories(
                        nutrition != null
                                ? nutrition.getCalories()
                                : 0
                )

                .targetCalories(
                        goal != null
                                ? goal.getTargetCalories()
                                : null
                )

                .todayProtein(
                        nutrition != null
                                ? nutrition.getProteinG()
                                : BigDecimal.ZERO
                )

                .targetProtein(
                        goal != null
                                ? goal.getTargetProteinG()
                                : null
                )

                .todayWater(
                        nutrition != null
                                ? nutrition.getWaterLiters()
                                : BigDecimal.ZERO
                )

                .targetWater(
                        goal != null
                                ? goal.getTargetWaterLiters()
                                : null
                )

                .weeklyWorkouts(weeklyWorkouts)
                .weeklyWorkoutMinutes(weeklyWorkoutMinutes)

                .build();
    }


    // =========================================================
    // TODAY
    // =========================================================

    public TodayDashboardResponse getTodayDashboard(String email) {

        User user = getUser(email);

        Long userId = user.getUserId();

        LocalDate today = LocalDate.now();

        DailyActivity activity =
                dailyActivityRepository
                        .findByUser_UserIdAndDate(userId, today)
                        .orElse(null);

        NutritionLog nutrition =
                nutritionLogRepository
                        .findByUser_UserIdAndDate(userId, today)
                        .orElse(null);

        Goal goal =
                goalRepository
                        .findByUser_UserId(userId)
                        .orElse(null);

        return TodayDashboardResponse.builder()

                .date(today.toString())

                .steps(
                        activity != null
                                ? activity.getSteps()
                                : 0
                )

                .targetSteps(
                        goal != null
                                ? goal.getTargetSteps()
                                : null
                )

                .calories(
                        nutrition != null
                                ? nutrition.getCalories()
                                : 0
                )

                .targetCalories(
                        goal != null
                                ? goal.getTargetCalories()
                                : null
                )

                .protein(
                        nutrition != null
                                ? nutrition.getProteinG()
                                : BigDecimal.ZERO
                )

                .targetProtein(
                        goal != null
                                ? goal.getTargetProteinG()
                                : null
                )

                .water(
                        nutrition != null
                                ? nutrition.getWaterLiters()
                                : BigDecimal.ZERO
                )

                .targetWater(
                        goal != null
                                ? goal.getTargetWaterLiters()
                                : null
                )

                .activeMinutes(
                        activity != null
                                ? activity.getActiveMinutes()
                                : 0
                )

                .workoutMinutes(
                        activity != null
                                ? activity.getWorkoutMinutes()
                                : 0
                )

                .build();
    }


    // =========================================================
    // WEEKLY DASHBOARD
    // =========================================================

    public WeeklyDashboardResponse getWeeklyDashboard(String email) {

        User user = getUser(email);

        Long userId = user.getUserId();

        LocalDate today = LocalDate.now();

        LocalDate startDate =
                today.with(DayOfWeek.MONDAY);

        LocalDate endDate =
                startDate.plusDays(6);

        LocalDateTime start =
                startDate.atStartOfDay();

        LocalDateTime end =
                endDate.plusDays(1)
                        .atStartOfDay()
                        .minusNanos(1);

        // Workouts
        List<WorkoutSession> sessions =
                workoutSessionRepository
                        .findByUser_UserIdAndStartedAtBetween(
                                userId,
                                start,
                                end
                        );

        long totalWorkouts = sessions.size();

        long completedWorkouts =
                sessions.stream()
                        .filter(session ->
                                session.getStatus()
                                        == WorkoutSessionStatus.COMPLETED
                        )
                        .count();

        long totalWorkoutMinutes =
                sessions.stream()
                        .filter(session ->
                                session.getDurationMinutes() != null
                        )
                        .mapToLong(WorkoutSession::getDurationMinutes)
                        .sum();

        // Activity
        List<DailyActivity> activities =
                dailyActivityRepository
                        .findByUser_UserIdOrderByDateDesc(userId)
                        .stream()
                        .filter(activity ->
                                !activity.getDate().isBefore(startDate)
                                        && !activity.getDate().isAfter(endDate)
                        )
                        .toList();

        long totalSteps =
                activities.stream()
                        .filter(activity ->
                                activity.getSteps() != null
                        )
                        .mapToLong(DailyActivity::getSteps)
                        .sum();

        long totalActiveMinutes =
                activities.stream()
                        .filter(activity ->
                                activity.getActiveMinutes() != null
                        )
                        .mapToLong(DailyActivity::getActiveMinutes)
                        .sum();

        return WeeklyDashboardResponse.builder()

                .startDate(startDate.toString())
                .endDate(endDate.toString())

                .totalWorkouts(totalWorkouts)
                .completedWorkouts(completedWorkouts)
                .totalWorkoutMinutes(totalWorkoutMinutes)

                .totalSteps(totalSteps)
                .totalActiveMinutes(totalActiveMinutes)

                .build();
    }


    // =========================================================
    // USER
    // =========================================================

    private User getUser(String email) {

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }
}