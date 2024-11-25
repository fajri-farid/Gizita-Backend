package com.example.demo.Model;

import lombok.Data;

@Data
public class GPTRequest {
    private String model;
    private String prompt;
    private int maxTokens;
    private double temperature;

    public GPTRequest(String model, String prompt, int maxTokens, double temperature) {
        this.model = model;
        this.prompt = prompt;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
    }
}
