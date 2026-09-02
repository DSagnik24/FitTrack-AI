package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.WeightLogRequest;
import com.forgefit.forgeFit_Backend.dto.WeightLogResponse;
import com.forgefit.forgeFit_Backend.service.WeightLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weight-logs")
@RequiredArgsConstructor
public class WeightLogController {

    private final WeightLogService weightLogService;

    @PostMapping
    public ResponseEntity<WeightLogResponse> createWeightLog(
            Authentication authentication,
            @Valid @RequestBody WeightLogRequest request
    ) {

        return ResponseEntity.ok(
                weightLogService.createWeightLog(
                        authentication.getName(),
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<WeightLogResponse>> getAllWeightLogs(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                weightLogService.getAllWeightLogs(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{logId}")
    public ResponseEntity<WeightLogResponse> getWeightLog(
            @PathVariable Long logId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                weightLogService.getWeightLog(
                        authentication.getName(),
                        logId
                )
        );
    }

    @PutMapping("/{logId}")
    public ResponseEntity<WeightLogResponse> updateWeightLog(
            @PathVariable Long logId,
            Authentication authentication,
            @Valid @RequestBody WeightLogRequest request
    ) {

        return ResponseEntity.ok(
                weightLogService.updateWeightLog(
                        authentication.getName(),
                        logId,
                        request
                )
        );
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteWeightLog(
            @PathVariable Long logId,
            Authentication authentication
    ) {

        weightLogService.deleteWeightLog(
                authentication.getName(),
                logId
        );

        return ResponseEntity.noContent().build();
    }
}