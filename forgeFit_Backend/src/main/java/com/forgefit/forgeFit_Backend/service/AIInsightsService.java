package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.AIInsightsResponse;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.ThinkingConfig;
import com.google.genai.types.ThinkingLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIInsightsService {

    private final Client geminiClient;
    private final AIContextService aiContextService;

    public AIInsightsResponse generateInsights(String email) {

        System.out.println(">>> Building context for AI insights...");

        String userContext = aiContextService.buildContext(email);

        String prompt = """
                You are ForgeFit AI, a personal fitness assistant.

                Analyze the user's fitness data below and generate 3 to 5
                useful, personalized fitness insights.

                Focus on:
                - Goal progress
                - Weight progress
                - Workout consistency
                - Steps
                - Calories
                - Protein
                - Water
                - Any obvious areas that need attention

                Rules:
                - Use only the data provided.
                - Do not invent numbers or facts.
                - Keep each insight concise.
                - Make each insight actionable.
                - Return ONLY the insights.
                - Put each insight on a separate line.
                - Do not use numbering.
                - Do not use bullet points.
                - Do not add an introduction or conclusion.

                USER'S FORGEFIT DATA
                ====================

                %s
                """.formatted(userContext);

        System.out.println(">>> Sending insights request to Gemini...");

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .thinkingConfig(
                                ThinkingConfig.builder()
                                        .thinkingLevel(new ThinkingLevel("minimal"))
                                        .build()
                        )
                        .maxOutputTokens(300)
                        .build();

        GenerateContentResponse response =
                geminiClient.models.generateContent(
                        "gemini-3.6-flash",
                        prompt,
                        config
                );

        System.out.println(">>> Gemini insights received!");

        String text = response.text();

        List<String> insights = Arrays.stream(text.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(line -> line.replaceFirst("^[-*•]\\s*", ""))
                .map(line -> line.replaceFirst("^\\d+[.)]\\s*", ""))
                .toList();

        return AIInsightsResponse.builder()
                .insights(insights)
                .build();
    }
}