package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.WeightLogRequest;
import com.forgefit.forgeFit_Backend.dto.WeightLogResponse;
import com.forgefit.forgeFit_Backend.entity.User;
import com.forgefit.forgeFit_Backend.entity.WeightLog;
import com.forgefit.forgeFit_Backend.repository.UserRepository;
import com.forgefit.forgeFit_Backend.repository.WeightLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeightLogService {

    private final WeightLogRepository weightLogRepository;
    private final UserRepository userRepository;

    public WeightLogResponse createWeightLog(
            String email,
            WeightLogRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WeightLog weightLog = WeightLog.builder()
                .user(user)
                .weightKg(request.getWeightKg())
                .date(request.getDate())
                .notes(request.getNotes())
                .build();

        WeightLog saved =
                weightLogRepository.save(weightLog);

        return mapToResponse(saved);
    }

    public List<WeightLogResponse> getAllWeightLogs(
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return weightLogRepository
                .findByUser_UserIdOrderByDateDesc(
                        user.getUserId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public WeightLogResponse getWeightLog(
            String email,
            Long logId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WeightLog weightLog =
                weightLogRepository
                        .findByIdAndUser_UserId(
                                logId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Weight log not found"
                                )
                        );

        return mapToResponse(weightLog);
    }

    public WeightLogResponse updateWeightLog(
            String email,
            Long logId,
            WeightLogRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WeightLog weightLog =
                weightLogRepository
                        .findByIdAndUser_UserId(
                                logId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Weight log not found"
                                )
                        );

        weightLog.setWeightKg(request.getWeightKg());
        weightLog.setDate(request.getDate());
        weightLog.setNotes(request.getNotes());

        WeightLog updated =
                weightLogRepository.save(weightLog);

        return mapToResponse(updated);
    }

    public void deleteWeightLog(
            String email,
            Long logId
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        WeightLog weightLog =
                weightLogRepository
                        .findByIdAndUser_UserId(
                                logId,
                                user.getUserId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Weight log not found"
                                )
                        );

        weightLogRepository.delete(weightLog);
    }

    private WeightLogResponse mapToResponse(
            WeightLog weightLog
    ) {

        return WeightLogResponse.builder()
                .id(weightLog.getId())
                .userId(
                        weightLog.getUser().getUserId()
                )
                .weightKg(
                        weightLog.getWeightKg()
                )
                .date(
                        weightLog.getDate()
                )
                .notes(
                        weightLog.getNotes()
                )
                .createdAt(
                        weightLog.getCreatedAt()
                )
                .build();
    }
}