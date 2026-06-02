package com.salehin.AiCvEvaluator.controller;

import com.salehin.AiCvEvaluator.dto.CvEvaluationResponse;
import com.salehin.AiCvEvaluator.service.GeminiService;
import com.salehin.AiCvEvaluator.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ai")
public class MainController {
    @Autowired
    GeminiService geminiService;
    @Autowired
    OllamaService ollamaService;

    @PostMapping("/gemini")
    public String testAi(@RequestBody String prompt){
        return geminiService.askGemini(prompt);
    }

    @PostMapping("/ollama")
    public String chat(@RequestBody String prompt){
        return ollamaService.ask(prompt);
    }

    @PostMapping("/analyzeCV")
    public CvEvaluationResponse evaluate(@RequestParam("image")MultipartFile image){
        return ollamaService.evaluateCv(image);
    }
}
