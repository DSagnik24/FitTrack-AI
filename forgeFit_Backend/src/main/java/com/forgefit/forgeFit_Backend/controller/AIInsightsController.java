package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.AIInsightsResponse;
import com.forgefit.forgeFit_Backend.service.AIInsightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIInsightsController {

    private final AIInsightsService aiInsightsService;

    @GetMapping("/insights")
    public ResponseEntity<AIInsightsResponse> getInsights(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                aiInsightsService.generateInsights(
                        authentication.getName()
                )
        );
    }
}