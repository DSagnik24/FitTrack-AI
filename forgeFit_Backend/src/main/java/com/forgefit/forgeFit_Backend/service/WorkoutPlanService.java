package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.WorkoutDayExerciseResponse;
import com.forgefit.forgeFit_Backend.dto.WorkoutDayResponse;
import com.forgefit.forgeFit_Backend.dto.WorkoutPlanRequest;
import com.forgefit.forgeFit_Backend.dto.WorkoutPlanResponse;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.entity.WorkoutPlan;
import com.forgefit.forgeFit_Backend.entity.WorkoutPlanExercise;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutPlanExerciseRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;
    private final WorkoutPlanExerciseRepository workoutPlanExerciseRepository;

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


        public WorkoutDayResponse getWorkoutDay(
                String email,
                Long workoutPlanId,
                Integer dayNumber
        ) {

            // ---------------------------------------------------------
            // Get user
            // ---------------------------------------------------------

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException("User not found")
                    );


            // ---------------------------------------------------------
            // Get workout plan belonging to this user
            // ---------------------------------------------------------

            WorkoutPlan workoutPlan =
                    workoutPlanRepository.findById(workoutPlanId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Workout plan not found"
                                    )
                            );


            // ---------------------------------------------------------
            // Security check
            // ---------------------------------------------------------

            if (!workoutPlan.getUser().getUserId()
                    .equals(user.getUserId())) {

                throw new RuntimeException(
                        "You do not have access to this workout plan"
                );
            }


            // ---------------------------------------------------------
            // Get exercises for selected day
            // ---------------------------------------------------------

            List<WorkoutPlanExercise> exercises =
                    workoutPlanExerciseRepository
                            .findByWorkoutPlan_IdAndDayNumberOrderByExerciseOrder(
                                    workoutPlanId,
                                    dayNumber
                            );


            if (exercises.isEmpty()) {
                throw new RuntimeException(
                        "No exercises found for day "
                                + dayNumber
                );
            }


            // ---------------------------------------------------------
            // Convert to response
            // ---------------------------------------------------------

            List<WorkoutDayExerciseResponse> exerciseResponses =
                    exercises.stream()
                            .map(exercise ->
                                    WorkoutDayExerciseResponse.builder()
                                            .exerciseId(
                                                    exercise
                                                            .getExercise()
                                                            .getId()
                                            )
                                            .exerciseName(
                                                    exercise
                                                            .getExercise()
                                                            .getName()
                                            )
                                            .description(
                                                    exercise
                                                            .getExercise()
                                                            .getDescription()
                                            )
                                            .muscleGroup(
                                                    exercise
                                                            .getExercise()
                                                            .getMuscleGroup()
                                            )
                                            .equipment(
                                                    exercise
                                                            .getExercise()
                                                            .getEquipment()
                                            )
                                            .sets(
                                                    exercise.getSets()
                                            )
                                            .reps(
                                                    exercise.getReps()
                                            )
                                            .weightKg(
                                                    exercise.getWeightKg()
                                            )
                                            .restSeconds(
                                                    exercise
                                                            .getRestSeconds()
                                            )
                                            .exerciseOrder(
                                                    exercise
                                                            .getExerciseOrder()
                                            )
                                            .build()
                            )
                            .toList();


            return WorkoutDayResponse.builder()
                    .workoutPlanId(workoutPlanId)
                    .dayNumber(dayNumber)
                    .dayName(
                            "Day " + dayNumber
                    )
                    .exercises(exerciseResponses)
                    .build();
        }
    }
