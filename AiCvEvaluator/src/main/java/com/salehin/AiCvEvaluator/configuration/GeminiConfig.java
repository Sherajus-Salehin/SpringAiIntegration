package com.salehin.AiCvEvaluator.configuration;
import org.springframework.beans.factory.annotation.Value;
import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {
    @Bean
    public Client geminiClient(@Value("${gemini.api.key}")String apiKey){
        System.out.println("Key starts with: "
                + apiKey.substring(0, 6));
        return Client.builder().apiKey(apiKey).build();
    }
}
