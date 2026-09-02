package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.DailyActivityRequest;
import com.forgefit.forgeFit_Backend.dto.DailyActivityResponse;
import com.forgefit.forgeFit_Backend.entity.DailyActivity;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.repository.DailyActivityRepository;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyActivityService {

    private final DailyActivityRepository dailyActivityRepository;
    private final UserRepository userRepository;

    public DailyActivityResponse createDailyActivity(
            String email,
            DailyActivityRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        if (dailyActivityRepository
                .findByUser_UserIdAndDate(
                        user.getUserId(),
                        request.getDate()
                )
                .isPresent()) {

            throw new RuntimeException(
                    "Daily activity already exists for this date"
            );
        }

        DailyActivity activity = DailyActivity.builder()
                .user(user)
                .date(request.getDate())
                .steps(request.getSteps())
                .caloriesBurned(request.getCaloriesBurned())
                .activeMinutes(request.getActiveMinutes())
                .workoutMinutes(request.getWorkoutMinutes())
                .waterLiters(request.getWaterLiters())
                .build();

        DailyActivity saved =
                dailyActivityRepository.save(activity);

        return mapToResponse(saved);
    }

    public List<DailyActivityResponse> getAllDailyActivities(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return dailyActivityRepository
                .findByUser_UserIdOrderByDateDesc(
                        user.getUserId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public DailyActivityResponse getDailyActivity(
            String email,
            Long activityId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        DailyActivity activity =
                dailyActivityRepository
                        .findByIdAndUser_UserId(
                                activityId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Daily activity not found"
                                )
                        );

        return mapToResponse(activity);
    }

    public DailyActivityResponse getDailyActivityByDate(
            String email,
            LocalDate date
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        DailyActivity activity =
                dailyActivityRepository
                        .findByUser_UserIdAndDate(
                                user.getUserId(),
                                date
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Daily activity not found for this date"
                                )
                        );

        return mapToResponse(activity);
    }

    public DailyActivityResponse updateDailyActivity(
            String email,
            Long activityId,
            DailyActivityRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        DailyActivity activity =
                dailyActivityRepository
                        .findByIdAndUser_UserId(
                                activityId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Daily activity not found"
                                )
                        );

        if (!activity.getDate().equals(request.getDate())) {

            if (dailyActivityRepository
                    .findByUser_UserIdAndDate(
                            user.getUserId(),
                            request.getDate()
                    )
                    .isPresent()) {

                throw new RuntimeException(
                        "Daily activity already exists for this date"
                );
            }
        }

        activity.setDate(request.getDate());
        activity.setSteps(request.getSteps());
        activity.setCaloriesBurned(
                request.getCaloriesBurned()
        );
        activity.setActiveMinutes(
                request.getActiveMinutes()
        );
        activity.setWorkoutMinutes(
                request.getWorkoutMinutes()
        );
        activity.setWaterLiters(
                request.getWaterLiters()
        );

        DailyActivity updated =
                dailyActivityRepository.save(activity);

        return mapToResponse(updated);
    }

    public void deleteDailyActivity(
            String email,
            Long activityId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        DailyActivity activity =
                dailyActivityRepository
                        .findByIdAndUser_UserId(
                                activityId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Daily activity not found"
                                )
                        );

        dailyActivityRepository.delete(activity);
    }

    private DailyActivityResponse mapToResponse(
            DailyActivity activity
    ) {

        return DailyActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUser().getUserId())
                .date(activity.getDate())
                .steps(activity.getSteps())
                .caloriesBurned(
                        activity.getCaloriesBurned()
                )
                .activeMinutes(
                        activity.getActiveMinutes()
                )
                .workoutMinutes(
                        activity.getWorkoutMinutes()
                )
                .waterLiters(
                        activity.getWaterLiters()
                )
                .createdAt(
                        activity.getCreatedAt()
                )
                .build();
    }
}