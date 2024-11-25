package com.example.demo.Service;

import com.example.demo.Model.GPTRequest;
import com.example.demo.Model.GPTResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GPTService {

    @Value("${chatgpt.api.url}")
    private String apiUrl;

    @Value("${chatgpt.api.key}")
    private String apiKey;

    private final OkHttpClient httpClient = new OkHttpClient();

    public GPTResponse sendRequest(String prompt) throws Exception {
        // Buat payload JSON
        GPTRequest gptRequest = new GPTRequest("gpt-4", prompt, 100, 0.7);
        String jsonBody = new ObjectMapper().writeValueAsString(gptRequest);

        // Buat RequestBody
        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.get("application/json; charset=utf-8")
        );

        // Buat HTTP Request
        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        // Kirim Request dan Dapatkan Response
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Request failed: " + response.code() + " " + response.message());
            }

            // Parse Response ke GPTResponse
            return new ObjectMapper().readValue(response.body().string(), GPTResponse.class);
        }
    }
}
