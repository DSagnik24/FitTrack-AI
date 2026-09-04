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
    private final AIContextService aiContextService;

    public AIResponse askAI(String message, String email) {

        System.out.println(">>> Building ForgeFit AI context...");

        String userContext = aiContextService.buildContext(email);

        String prompt = """
                You are ForgeFit AI, a personal fitness assistant.

                Use the user's ForgeFit data below to provide personalized,
                practical and concise fitness guidance.

                Do not invent user data.
                If some data is missing, simply work with the available data.

                USER'S FORGEFIT DATA
                ====================

                %s

                USER'S QUESTION
                ===============

                %s

                Answer the user's question based on their actual ForgeFit data.
                """.formatted(userContext, message);

        System.out.println(">>> Sending personalized request to Gemini...");

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .thinkingConfig(
                                ThinkingConfig.builder()
                                        .thinkingLevel(new ThinkingLevel("minimal"))
                                        .build()
                        )
                        .maxOutputTokens(500)
                        .build();

        GenerateContentResponse response =
                geminiClient.models.generateContent(
                        "gemini-3.6-flash",
                        prompt,
                        config
                );

        System.out.println(">>> Gemini response received!");

        return AIResponse.builder()
                .response(response.text())
                .build();
    }
}