package com.salehin.AiCvEvaluator.service;

import com.salehin.AiCvEvaluator.dto.OllamaResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OllamaService {
    private final WebClient webClient;

    public String ask(String prompt) {
        Map<String,Object> reqBody= Map.of(
                "model","gemma3:4b",
                "prompt",prompt,
                "stream",false
        );
        OllamaResponse response= webClient.post()
                .uri("/api/generate")
                .bodyValue(reqBody)
                .retrieve()
                .bodyToMono(OllamaResponse.class)
                .block();
        return response.getResponse();
    }
}
