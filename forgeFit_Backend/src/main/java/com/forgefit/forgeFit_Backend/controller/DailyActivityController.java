package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.DailyActivityRequest;
import com.forgefit.forgeFit_Backend.dto.DailyActivityResponse;
import com.forgefit.forgeFit_Backend.service.DailyActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/daily-activities")
@RequiredArgsConstructor
public class DailyActivityController {

    private final DailyActivityService dailyActivityService;

    @PostMapping
    public ResponseEntity<DailyActivityResponse> createDailyActivity(
            Authentication authentication,
            @Valid @RequestBody DailyActivityRequest request
    ) {

        return ResponseEntity.ok(
                dailyActivityService.createDailyActivity(
                        authentication.getName(),
                        request
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<DailyActivityResponse>>
    getAllDailyActivities(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                dailyActivityService.getAllDailyActivities(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<DailyActivityResponse> getDailyActivity(
            @PathVariable Long activityId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                dailyActivityService.getDailyActivity(
                        authentication.getName(),
                        activityId
                )
        );
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<DailyActivityResponse>
    getDailyActivityByDate(
            @PathVariable LocalDate date,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                dailyActivityService.getDailyActivityByDate(
                        authentication.getName(),
                        date
                )
        );
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<DailyActivityResponse>
    updateDailyActivity(
            @PathVariable Long activityId,
            Authentication authentication,
            @Valid @RequestBody DailyActivityRequest request
    ) {

        return ResponseEntity.ok(
                dailyActivityService.updateDailyActivity(
                        authentication.getName(),
                        activityId,
                        request
                )
        );
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteDailyActivity(
            @PathVariable Long activityId,
            Authentication authentication
    ) {

        dailyActivityService.deleteDailyActivity(
                authentication.getName(),
                activityId
        );

        return ResponseEntity.noContent().build();
    }
}