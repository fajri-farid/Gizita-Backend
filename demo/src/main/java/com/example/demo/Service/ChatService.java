package com.example.demo.Service;

import com.example.demo.Entity.ChatMessage;
import com.example.demo.Repository.ChatMessageRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Service
public class ChatService {
    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatMessage getChatResponse(String userMessage) throws IOException {
        // Membuat JSON payload
        String json = "{\n" +
                "  \"model\": \"text-davinci-003\",\n" +
                "  \"prompt\": \"" + userMessage + "\",\n" +
                "  \"max_tokens\": 100\n" +
                "}";

        // Membuat permintaan HTTP
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json")
        );

        Request request = new Request().Builder()
                .url(apiUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        // Mengirim permintaan dan menangani respons
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            String botResponse = rootNode.get("choices").get(0).get("text").asText().trim();

            // Simpan chat ke database
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setUserMessage(userMessage);
            chatMessage.setBotResponse(botResponse);
            chatMessageRepo.save(chatMessage);

            return chatMessage;
        } else {
            throw new IOException("Error in OpenAI API call: " + response.body().string());
        }
    }
}
