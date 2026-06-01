package com.salehin.AiCvEvaluator.controller;

import com.salehin.AiCvEvaluator.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class MainController {
    @Autowired
    GeminiService geminiService;

    @GetMapping("/ask")
    public String testAi(@RequestBody String prompt){
        return geminiService.askGemini(prompt);
    }
}
