package com.salehin.AiCvEvaluator.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.salehin.AiCvEvaluator.dto.CvEvaluationResponse;
import com.salehin.AiCvEvaluator.dto.OllamaResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OllamaService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    //CV Evaluation prompt is at the bottom of this class
    public CvEvaluationResponse evaluateCv(MultipartFile image) {
        try{
            String base64= Base64
                    .getEncoder()
                    .encodeToString(
                            image.getBytes()
                    );
            Map<String,Object> requestBody =
                    Map.of(
                            "model", "gemma3:4b",
                            "prompt", prompt,
                            "images", List.of(base64),
                            "stream", false
                    );
            JsonNode response= webClient.post()
                    .uri("/api/generate")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            String Airesponse=response.get("response")
                    .asText();
            CvEvaluationResponse dto= objectMapper.readValue(Airesponse, CvEvaluationResponse.class);

            return dto;
        }catch (Exception e){
            throw new RuntimeException("CV evaluation failed",e);
        }
    }




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
private final String prompt= """
        You are an expert CV evaluator and recruitment consultant.
        Your task is to analyze a CV provided as an image and evaluate its quality based on professional hiring standards.
        Evaluate the CV across the following dimensions:
        1. Formatting & Structure (0-10)
           - Clear sections (Education, Experience, Skills, etc.)
           - Readability and layout
           - Proper alignment and spacing
        2. Content Quality (0-10)
           - Clarity of descriptions
           - Use of action verbs
           - Relevance of information
        3. Skills & Technical Strength (0-10)
           - Presence of relevant skills
           - Depth of expertise
           - Alignment with industry expectations
        4. Experience & Impact (0-10)
           - Quantifiable achievements
           - Real-world impact
           - Internship/project relevance
        5. Overall Professionalism (0-10)
           - Grammar and spelling
           - Tone and presentation
           - Completeness
        After evaluating all categories:
        - Calculate TOTAL SCORE out of 50
        - Convert it to a percentage (0-100)
        IMPORTANT:
        - Be strict and realistic (do not give overly generous scores)
        - Do not assume missing information
        - Base evaluation only on visible content in the CV image
        Return your response ONLY in the following JSON format:
        {
          "formatting_score": number,
          "content_score": number,
          "skills_score": number,
          "experience_score": number,
          "professionalism_score": number,
          "total_score": number,
          "percentage": number,
          "strengths": ["point1", "point2", "point3"],
          "weaknesses": ["point1", "point2", "point3"],
          "suggestions": ["improvement1", "improvement2", "improvement3"]
        }
        Do NOT include any explanation outside JSON.
        Ensure all fields are present.
        Ensure numbers are integers.
        """;
}
