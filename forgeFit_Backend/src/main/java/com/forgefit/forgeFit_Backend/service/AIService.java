package com.forgefit.forgeFit_Backend.service;

import com.forgefit.forgeFit_Backend.dto.AIResponse;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AIService {

    private final OpenAIClient openAIClient;

    public AIResponse askAI(String message) {

        ResponseCreateParams params =
                ResponseCreateParams.builder()
                        .model("gpt-5.6")
                        .input(message)
                        .build();

        Response response =
                openAIClient.responses().create(params);

        String output = response.output().stream()
                .flatMap(item -> item.message().stream())
                .flatMap(messageOutput -> messageOutput.content().stream())
                .flatMap(content -> content.outputText().stream())
                .map(outputText -> outputText.text())
                .collect(Collectors.joining());

        return AIResponse.builder()
                .response(output)
                .build();
    }
}