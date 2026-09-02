package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.AIResponse;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.ThinkingConfig;
import com.google.genai.types.ThinkingLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {

    private final Client geminiClient;

    public AIResponse askAI(String message) {

        System.out.println(">>> Sending request to Gemini...");

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .thinkingConfig(
                                ThinkingConfig.builder()
                                        .thinkingLevel(new ThinkingLevel("minimal"))
                                        .build()
                        )
                        .maxOutputTokens(200)
                        .build();

        GenerateContentResponse response =
                geminiClient.models.generateContent(
                        "gemini-3.6-flash",
                        message,
                        config
                );

        System.out.println(">>> Gemini response received!");

        return AIResponse.builder()
                .response(response.text())
                .build();
    }
}