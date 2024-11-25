package com.example.demo.Model;

import lombok.Data;

@Data
public class GPTResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private Usage usage;
    private Choice[] choices;

    @Data
    public static class Usage {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;
    }

    @Data
    public static class Choice {
        private String text;
        private int index;
        private String finishReason;
    }
}