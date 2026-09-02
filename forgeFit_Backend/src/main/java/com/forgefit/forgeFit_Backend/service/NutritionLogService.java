package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.NutritionLogRequest;
import com.forgefit.forgeFit_Backend.dto.NutritionLogResponse;
import com.forgefit.forgeFit_Backend.entity.NutritionLog;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.repository.NutritionLogRepository;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NutritionLogService {

    private final NutritionLogRepository nutritionLogRepository;
    private final UserRepository userRepository;

    public NutritionLogResponse createNutritionLog(
            String email,
            NutritionLogRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        if (nutritionLogRepository
                .findByUser_UserIdAndDate(
                        user.getUserId(),
                        request.getDate()
                )
                .isPresent()) {

            throw new RuntimeException(
                    "Nutrition log already exists for this date"
            );
        }

        NutritionLog nutritionLog = NutritionLog.builder()
                .user(user)
                .date(request.getDate())
                .calories(request.getCalories())
                .proteinG(request.getProteinG())
                .carbohydratesG(request.getCarbohydratesG())
                .fatsG(request.getFatsG())
                .fiberG(request.getFiberG())
                .waterLiters(request.getWaterLiters())
                .notes(request.getNotes())
                .build();

        NutritionLog saved =
                nutritionLogRepository.save(nutritionLog);

        return mapToResponse(saved);
    }

    public List<NutritionLogResponse> getAllNutritionLogs(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return nutritionLogRepository
                .findByUser_UserIdOrderByDateDesc(
                        user.getUserId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public NutritionLogResponse getNutritionLog(
            String email,
            Long logId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        NutritionLog nutritionLog =
                nutritionLogRepository
                        .findByIdAndUser_UserId(
                                logId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Nutrition log not found"
                                )
                        );

        return mapToResponse(nutritionLog);
    }

    public NutritionLogResponse getNutritionLogByDate(
            String email,
            LocalDate date
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        NutritionLog nutritionLog =
                nutritionLogRepository
                        .findByUser_UserIdAndDate(
                                user.getUserId(),
                                date
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Nutrition log not found for this date"
                                )
                        );

        return mapToResponse(nutritionLog);
    }

    public NutritionLogResponse updateNutritionLog(
            String email,
            Long logId,
            NutritionLogRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        NutritionLog nutritionLog =
                nutritionLogRepository
                        .findByIdAndUser_UserId(
                                logId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Nutrition log not found"
                                )
                        );

        if (!nutritionLog.getDate().equals(request.getDate())) {

            if (nutritionLogRepository
                    .findByUser_UserIdAndDate(
                            user.getUserId(),
                            request.getDate()
                    )
                    .isPresent()) {

                throw new RuntimeException(
                        "Nutrition log already exists for this date"
                );
            }
        }

        nutritionLog.setDate(request.getDate());
        nutritionLog.setCalories(request.getCalories());
        nutritionLog.setProteinG(request.getProteinG());
        nutritionLog.setCarbohydratesG(
                request.getCarbohydratesG()
        );
        nutritionLog.setFatsG(request.getFatsG());
        nutritionLog.setFiberG(request.getFiberG());
        nutritionLog.setWaterLiters(request.getWaterLiters());
        nutritionLog.setNotes(request.getNotes());

        NutritionLog updated =
                nutritionLogRepository.save(nutritionLog);

        return mapToResponse(updated);
    }

    public void deleteNutritionLog(
            String email,
            Long logId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        NutritionLog nutritionLog =
                nutritionLogRepository
                        .findByIdAndUser_UserId(
                                logId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Nutrition log not found"
                                )
                        );

        nutritionLogRepository.delete(nutritionLog);
    }

    private NutritionLogResponse mapToResponse(
            NutritionLog nutritionLog
    ) {

        return NutritionLogResponse.builder()
                .id(nutritionLog.getId())
                .userId(
                        nutritionLog.getUser().getUserId()
                )
                .date(
                        nutritionLog.getDate()
                )
                .calories(
                        nutritionLog.getCalories()
                )
                .proteinG(
                        nutritionLog.getProteinG()
                )
                .carbohydratesG(
                        nutritionLog.getCarbohydratesG()
                )
                .fatsG(
                        nutritionLog.getFatsG()
                )
                .fiberG(
                        nutritionLog.getFiberG()
                )
                .waterLiters(
                        nutritionLog.getWaterLiters()
                )
                .notes(
                        nutritionLog.getNotes()
                )
                .createdAt(
                        nutritionLog.getCreatedAt()
                )
                .build();
    }
}