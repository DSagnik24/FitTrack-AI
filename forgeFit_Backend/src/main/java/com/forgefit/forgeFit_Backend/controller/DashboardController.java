package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.DashboardResponse;
import com.forgefit.forgeFit_Backend.dto.TodayDashboardResponse;
import com.forgefit.forgeFit_Backend.dto.WeeklyDashboardResponse;
import com.forgefit.forgeFit_Backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;


    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                dashboardService.getDashboard(email)
        );
    }


    @GetMapping("/today")
    public ResponseEntity<TodayDashboardResponse> getTodayDashboard(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                dashboardService.getTodayDashboard(email)
        );
    }


    @GetMapping("/weekly")
    public ResponseEntity<WeeklyDashboardResponse> getWeeklyDashboard(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                dashboardService.getWeeklyDashboard(email)
        );
    }
}