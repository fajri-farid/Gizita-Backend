package com.example.demo.Service;

import com.example.demo.Entity.ChatMessage;
import com.example.demo.Repository.ChatMessageRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChatService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatMessage getChatResponse(String userMessage) throws IOException {
        // Membuat JSON payload menggunakan ObjectMapper untuk keamanan
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "gpt-4-0314");

        ArrayNode messages = objectMapper.createArrayNode();
        ObjectNode message = objectMapper.createObjectNode();
        message.put("role", "user");
        message.put("content", userMessage);
        messages.add(message);

        requestBody.set("messages", messages);
        requestBody.put("max_tokens", 100);

        String json = requestBody.toString(); // Ini memastikan JSON valid

        // Membuat request body
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        // Membuat request
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        // Mengirim permintaan dan menangani respons
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            String botResponse = rootNode.get("choices").get(0).get("message").get("content").asText().trim();

            // Simpan chat ke database
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setUserMessage(userMessage);
            chatMessage.setBotResponse(botResponse);
            chatMessageRepo.save(chatMessage);

            return chatMessage;
        } else {
            String errorResponse = response.body().string();
            throw new IOException("Error in OpenAI API call: " + errorResponse);
        }
    }

}
