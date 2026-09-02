package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.NutritionLogRequest;
import com.forgefit.forgeFit_Backend.dto.NutritionLogResponse;
import com.forgefit.forgeFit_Backend.service.NutritionLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/nutrition-logs")
@RequiredArgsConstructor
public class NutritionLogController {

    private final NutritionLogService nutritionLogService;

    @PostMapping
    public ResponseEntity<NutritionLogResponse> createNutritionLog(
            Authentication authentication,
            @Valid @RequestBody NutritionLogRequest request
    ) {

        return ResponseEntity.ok(
                nutritionLogService.createNutritionLog(
                        authentication.getName(),
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<NutritionLogResponse>>
    getAllNutritionLogs(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                nutritionLogService.getAllNutritionLogs(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{logId}")
    public ResponseEntity<NutritionLogResponse> getNutritionLog(
            @PathVariable Long logId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                nutritionLogService.getNutritionLog(
                        authentication.getName(),
                        logId
                )
        );
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<NutritionLogResponse>
    getNutritionLogByDate(
            @PathVariable LocalDate date,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                nutritionLogService.getNutritionLogByDate(
                        authentication.getName(),
                        date
                )
        );
    }

    @PutMapping("/{logId}")
    public ResponseEntity<NutritionLogResponse>
    updateNutritionLog(
            @PathVariable Long logId,
            Authentication authentication,
            @Valid @RequestBody NutritionLogRequest request
    ) {

        return ResponseEntity.ok(
                nutritionLogService.updateNutritionLog(
                        authentication.getName(),
                        logId,
                        request
                )
        );
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteNutritionLog(
            @PathVariable Long logId,
            Authentication authentication
    ) {

        nutritionLogService.deleteNutritionLog(
                authentication.getName(),
                logId
        );

        return ResponseEntity.noContent().build();
    }
}