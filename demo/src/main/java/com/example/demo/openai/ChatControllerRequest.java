package com.example.demo.openai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChatControllerRequest {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/chatbot")
    public String getOpenAIResponse(@RequestBody String prompt) {
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest("gpt-4o", prompt);

        ChatControllerResponse response = restTemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                chatCompletionRequest,
                ChatControllerResponse.class
        );

        if (response != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getMessage().getContent();
        }

        return "No response from OpenAI API";
    }


    @PostMapping("/chatbot/saran-makanan")
    public String getNutritionalSuggestion() {
        String prompt = generateNutritionalPrompt();

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest("gpt-4o", prompt);

        ChatControllerResponse response = restTemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                chatCompletionRequest,
                ChatControllerResponse.class
        );

        if (response != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getMessage().getContent();
        }

        return "No response from OpenAI API";
    }

    private String generateNutritionalPrompt() {
        return "Buatkan rekomendasi makanan bergizi untuk sarapan, makan siang, dan makan malam yang relevan untuk mencegah stunting. Untuk makanannya sesuaikan preferensi makanan untuk orang menengah atau kebawah karena mereka adalah target kami ini. Setiap rekomendasi harus berisi saran makanan dan alasan mengapa makanan tersebut penting dan pastikan output yang kamu berikan relevan. Format respons dalam bentuk JSON, di mana elemen HTML berada dalam properti \"output\". Contoh:\n" +
                "\n" +
                "{\n" +
                "  \"output\": \"<div>\n" +
                "    <h3>Rekomendasi Makanan Bergizi</h3>\n" +
                "    <ul>\n" +
                "        <li><strong>Sarapan:</strong> Bubur ayam dengan telur rebus dan segelas susu. <br> Alasan: Bubur ayam mudah dicerna dan kaya protein untuk membantu pertumbuhan, sementara telur dan susu menyediakan protein serta kalsium untuk memperkuat tulang.</li>\n" +
                "        <li><strong>Makan Siang:</strong> Ikan goreng, nasi merah, sayur asem, dan buah pepaya. <br> Alasan: Ikan goreng mengandung omega-3 untuk perkembangan otak, nasi merah kaya serat, sayur asem sumber vitamin, dan pepaya kaya vitamin C untuk meningkatkan daya tahan tubuh.</li>\n" +
                "        <li><strong>Makan Malam:</strong> Sup ayam, kentang rebus, dan jus alpukat. <br> Alasan: Sup ayam memberikan protein dan cairan yang membantu regenerasi sel, kentang sebagai sumber energi, dan jus alpukat kaya lemak sehat untuk mendukung pertumbuhan otak.</li>\n" +
                "    </ul>\n" +
                "  </div>\"\n" +
                "}";
    }
}
