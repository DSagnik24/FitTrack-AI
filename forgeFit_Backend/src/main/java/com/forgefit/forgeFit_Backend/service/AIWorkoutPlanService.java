package com.forgefit.forgeFit_Backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forgefit.forgeFit_Backend.dto.AIWorkoutPlanDayResponse;
import com.forgefit.forgeFit_Backend.dto.AIWorkoutPlanExerciseResponse;
import com.forgefit.forgeFit_Backend.dto.AIWorkoutPlanRequest;
import com.forgefit.forgeFit_Backend.dto.AIWorkoutPlanResponse;
import com.forgefit.forgeFit_Backend.entity.Exercise;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.entity.WorkoutPlan;
import com.forgefit.forgeFit_Backend.entity.WorkoutPlanExercise;
import com.forgefit.forgeFit_Backend.repository.ExerciseRepository;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutPlanExerciseRepository;
import com.forgefit.forgeFit_Backend.repository.WorkoutPlanRepository;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.ThinkingConfig;
import com.google.genai.types.ThinkingLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIWorkoutPlanService {

    private final Client geminiClient;

    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutPlanExerciseRepository workoutPlanExerciseRepository;


    @Transactional
    public AIWorkoutPlanResponse generateWorkoutPlan(
            String email,
            AIWorkoutPlanRequest request
    ) {

        // ---------------------------------------------------------
        // 1. Get user
        // ---------------------------------------------------------

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );


        // ---------------------------------------------------------
        // 2. Get exercises from database
        // ---------------------------------------------------------

        List<Exercise> availableExercises =
                exerciseRepository.findAll();

        if (availableExercises.isEmpty()) {
            throw new RuntimeException(
                    "No exercises are available in the database."
            );
        }


        // ---------------------------------------------------------
        // 3. Build database exercise list
        // ---------------------------------------------------------

        String availableExerciseNames =
                availableExercises.stream()
                        .map(Exercise::getName)
                        .collect(Collectors.joining("\n"));


        // ---------------------------------------------------------
        // 4. Additional instructions
        // ---------------------------------------------------------

        String additionalInstructions =
                request.getAdditionalInstructions() == null
                        ? "None"
                        : request.getAdditionalInstructions();


        // ---------------------------------------------------------
        // 5. AI prompt
        // ---------------------------------------------------------

        String prompt = """
                You are ForgeFit AI.

                Generate a practical multi-day workout plan.

                Goal: %s
                Days per week: %d
                Workout duration per day: %d minutes
                Instructions: %s

                DATABASE EXERCISES:
                %s

                STRICT RULES:

                - Generate EXACTLY %d workout days.
                - dayNumber must start at 1.
                - dayNumber must end at %d.
                - Every day must contain exercises.
                - Use ONLY exercises from the database list.
                - Copy exercise names EXACTLY.
                - Never invent or rename exercises.
                - You may reuse an exercise on different days.
                - Avoid unnecessary repetition within the same day.
                - Keep each day's workout realistic for the requested duration.
                - sets: 1-6
                - reps: 1-30
                - restSeconds: 30-300
                - weightKg must be a number.
                - Use 0 when weight is unknown.
                - exerciseOrder starts at 1 for each day.

                Return ONLY valid JSON.
                No markdown.
                No explanation.

                JSON FORMAT:

                {
                  "name": "Workout plan name",
                  "description": "Short description",
                  "days": [
                    {
                      "dayNumber": 1,
                      "name": "Day 1 workout name",
                      "exercises": [
                        {
                          "exerciseName": "Exact database name",
                          "sets": 3,
                          "reps": 10,
                          "weightKg": 0,
                          "restSeconds": 60,
                          "exerciseOrder": 1
                        }
                      ]
                    }
                  ]
                }

                FINAL CHECK:
                - Exactly %d days.
                - Every exerciseName exists in the database list.
                - Every day has at least one exercise.
                - Return ONLY JSON.
                """.formatted(
                request.getGoal(),
                request.getDaysPerWeek(),
                request.getWorkoutDurationMinutes(),
                additionalInstructions,
                availableExerciseNames,
                request.getDaysPerWeek(),
                request.getDaysPerWeek(),
                request.getDaysPerWeek()
        );


        // ---------------------------------------------------------
        // 6. Generate AI response
        // ---------------------------------------------------------

        System.out.println(">>> Generating AI workout plan...");

        long startTime = System.currentTimeMillis();


        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .thinkingConfig(
                                ThinkingConfig.builder()
                                        .thinkingLevel(
                                                new ThinkingLevel("minimal")
                                        )
                                        .build()
                        )
                        .maxOutputTokens(700)
                        .build();


        GenerateContentResponse response =
                geminiClient.models.generateContent(
                        "gemini-3.6-flash",
                        prompt,
                        config
                );


        long endTime = System.currentTimeMillis();

        System.out.println(
                ">>> Gemini generation time: "
                        + (endTime - startTime)
                        + " ms"
        );


        String aiText = response.text();

        System.out.println(">>> RAW AI RESPONSE:");
        System.out.println(aiText);
        System.out.println(">>> END RAW AI RESPONSE");


        // ---------------------------------------------------------
        // 7. Parse JSON
        // ---------------------------------------------------------

        try {

            if (aiText == null || aiText.isBlank()) {
                throw new RuntimeException(
                        "Gemini returned an empty response."
                );
            }


            aiText = aiText
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();


            ObjectMapper objectMapper =
                    new ObjectMapper();


            Map<String, Object> generatedPlan =
                    objectMapper.readValue(
                            aiText,
                            new TypeReference<>() {
                            }
                    );


            // -----------------------------------------------------
            // 8. Basic validation
            // -----------------------------------------------------

            String planName =
                    (String) generatedPlan.get("name");

            String description =
                    (String) generatedPlan.get("description");


            if (planName == null || planName.isBlank()) {
                throw new RuntimeException(
                        "AI did not generate a workout plan name."
                );
            }


            if (description == null) {
                description = "";
            }


            Object daysObject =
                    generatedPlan.get("days");


            if (daysObject == null) {
                throw new RuntimeException(
                        "AI did not generate workout days."
                );
            }


            List<Map<String, Object>> generatedDays =
                    objectMapper.convertValue(
                            daysObject,
                            new TypeReference<>() {
                            }
                    );


            // -----------------------------------------------------
            // 9. Validate number of days
            // -----------------------------------------------------

            if (generatedDays.size()
                    != request.getDaysPerWeek()) {

                throw new RuntimeException(
                        "AI generated "
                                + generatedDays.size()
                                + " days instead of "
                                + request.getDaysPerWeek()
                );
            }


            // -----------------------------------------------------
            // 10. Save WorkoutPlan
            // -----------------------------------------------------

            WorkoutPlan workoutPlan =
                    WorkoutPlan.builder()
                            .user(user)
                            .name(planName)
                            .description(description)
                            .build();


            WorkoutPlan savedPlan =
                    workoutPlanRepository.save(workoutPlan);


            List<AIWorkoutPlanDayResponse> dayResponses =
                    new ArrayList<>();


            // -----------------------------------------------------
            // 11. Process each day
            // -----------------------------------------------------

            for (Map<String, Object> generatedDay :
                    generatedDays) {


                Integer dayNumber =
                        generatedDay.get("dayNumber") == null
                                ? dayResponses.size() + 1
                                : ((Number) generatedDay
                                .get("dayNumber"))
                                .intValue();


                String dayName =
                        generatedDay.get("name") == null
                                ? "Day " + dayNumber
                                : generatedDay
                                .get("name")
                                .toString();


                Object dayExercisesObject =
                        generatedDay.get("exercises");


                if (dayExercisesObject == null) {
                    throw new RuntimeException(
                            "Day "
                                    + dayNumber
                                    + " has no exercises."
                    );
                }


                List<Map<String, Object>> generatedExercises =
                        objectMapper.convertValue(
                                dayExercisesObject,
                                new TypeReference<>() {
                                }
                        );


                if (generatedExercises.isEmpty()) {
                    throw new RuntimeException(
                            "Day "
                                    + dayNumber
                                    + " has no exercises."
                    );
                }


                List<AIWorkoutPlanExerciseResponse>
                        exerciseResponses =
                        new ArrayList<>();


                // -------------------------------------------------
                // 12. Process exercises in this day
                // -------------------------------------------------

                for (Map<String, Object> generatedExercise :
                        generatedExercises) {


                    String exerciseName =
                            generatedExercise
                                    .get("exerciseName")
                                    .toString();


                    Exercise exercise =
                            findExerciseFromAvailableList(
                                    exerciseName,
                                    availableExercises
                            );


                    Integer sets =
                            generatedExercise.get("sets") == null
                                    ? 3
                                    : ((Number) generatedExercise
                                    .get("sets"))
                                    .intValue();


                    Integer reps =
                            generatedExercise.get("reps") == null
                                    ? 10
                                    : ((Number) generatedExercise
                                    .get("reps"))
                                    .intValue();


                    Double weightKg =
                            generatedExercise.get("weightKg") == null
                                    ? 0.0
                                    : ((Number) generatedExercise
                                    .get("weightKg"))
                                    .doubleValue();


                    Integer restSeconds =
                            generatedExercise.get("restSeconds") == null
                                    ? 60
                                    : ((Number) generatedExercise
                                    .get("restSeconds"))
                                    .intValue();


                    Integer exerciseOrder =
                            generatedExercise.get("exerciseOrder") == null
                                    ? exerciseResponses.size() + 1
                                    : ((Number) generatedExercise
                                    .get("exerciseOrder"))
                                    .intValue();


                    // -------------------------------------------------
                    // Validate values
                    // -------------------------------------------------

                    if (sets < 1 || sets > 6) {
                        sets = 3;
                    }

                    if (reps < 1 || reps > 30) {
                        reps = 10;
                    }

                    if (restSeconds < 30 || restSeconds > 300) {
                        restSeconds = 60;
                    }

                    if (weightKg < 0) {
                        weightKg = 0.0;
                    }


                    // -------------------------------------------------
                    // Save exercise
                    // -------------------------------------------------

                    WorkoutPlanExercise planExercise =
                            WorkoutPlanExercise.builder()
                                    .workoutPlan(savedPlan)
                                    .exercise(exercise)
                                    .dayNumber(dayNumber)
                                    .sets(sets)
                                    .reps(reps)
                                    .weightKg(weightKg)
                                    .restSeconds(restSeconds)
                                    .exerciseOrder(exerciseOrder)
                                    .build();


                    workoutPlanExerciseRepository.save(
                            planExercise
                    );


                    // -------------------------------------------------
                    // Build exercise response
                    // -------------------------------------------------

                    exerciseResponses.add(
                            AIWorkoutPlanExerciseResponse.builder()
                                    .exerciseId(exercise.getId())
                                    .exerciseName(exercise.getName())
                                    .dayNumber(dayNumber)
                                    .sets(sets)
                                    .reps(reps)
                                    .weightKg(weightKg)
                                    .restSeconds(restSeconds)
                                    .exerciseOrder(exerciseOrder)
                                    .build()
                    );
                }


                // -------------------------------------------------
                // Build day response
                // -------------------------------------------------

                dayResponses.add(
                        AIWorkoutPlanDayResponse.builder()
                                .dayNumber(dayNumber)
                                .name(dayName)
                                .exercises(exerciseResponses)
                                .build()
                );
            }


            // -----------------------------------------------------
            // 13. Return final response
            // -----------------------------------------------------

            return AIWorkoutPlanResponse.builder()
                    .workoutPlanId(savedPlan.getId())
                    .name(savedPlan.getName())
                    .description(savedPlan.getDescription())
                    .days(dayResponses)
                    .build();


        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to generate AI workout plan: "
                            + e.getMessage(),
                    e
            );
        }
    }


    // =============================================================
    // Find exercise safely
    // =============================================================

    private Exercise findExerciseFromAvailableList(
            String exerciseName,
            List<Exercise> availableExercises
    ) {

        String normalizedName =
                normalizeExerciseName(exerciseName);


        return availableExercises.stream()
                .filter(exercise ->
                        normalizeExerciseName(
                                exercise.getName()
                        ).equalsIgnoreCase(normalizedName)
                )
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException(
                                "AI generated an exercise that does "
                                        + "not exist in the database: "
                                        + exerciseName
                        )
                );
    }


    // =============================================================
    // Normalize names
    // =============================================================

    private String normalizeExerciseName(
            String exerciseName
    ) {

        if (exerciseName == null) {
            return "";
        }

        return exerciseName
                .trim()
                .replaceAll("\\s+", " ");
    }


}