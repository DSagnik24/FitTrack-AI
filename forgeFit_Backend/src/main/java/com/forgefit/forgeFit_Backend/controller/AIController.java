package com.forgefit.forgeFit_Backend.controller;

import com.forgefit.forgeFit_Backend.dto.AIRequest;
import com.forgefit.forgeFit_Backend.dto.AIResponse;
import com.forgefit.forgeFit_Backend.service.AIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    @PostMapping("/chat")
    public ResponseEntity<AIResponse> chat(
            @Valid @RequestBody AIRequest request,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                aiService.askAI(request.getMessage())
        );
    }
}