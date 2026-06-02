package com.salehin.AiCvEvaluator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvEvaluationResponse {

    private Integer formatting_score;
    private Integer content_score;
    private Integer skills_score;
    private Integer experience_score;
    private Integer professionalism_score;

    private Integer total_score;
    private Double percentage;

    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> suggestions;
}