package com.example.demo.openai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatControllerRequest {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/chatbot")
    public ResponseEntity<Map<String, String>> getOpenAIResponse(@RequestBody String prompt) {
        // Membuat request untuk OpenAI API
        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest("gpt-4o", prompt);

        // Mengirimkan request ke OpenAI API
        ChatControllerResponse response = restTemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                chatCompletionRequest,
                ChatControllerResponse.class
        );

        // Jika response berhasil didapatkan
        if (response != null && !response.getChoices().isEmpty()) {
            String content = response.getChoices().get(0).getMessage().getContent();

            // Menambahkan tag HTML untuk memformat jawaban
            String formattedContent = content.replace("\n", "<br>"); // Menambahkan <br> untuk baris baru

            // Membuat respons JSON dengan output dalam format HTML
            Map<String, String> jsonResponse = new HashMap<>();
            jsonResponse.put("output", "<p>" + formattedContent + "</p>"); // Menambahkan tag <p> di sekitar jawaban

            // Mengembalikan respons JSON
            return ResponseEntity.ok(jsonResponse);
        }

        // Jika tidak ada respons dari OpenAI API
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("output", "<p>No response from OpenAI API</p>"); // Pesan error dalam tag HTML

        // Mengembalikan respons JSON error
        return ResponseEntity.ok(errorResponse);
    }


    private String generateNutritionalPrompt() {
        return "Hasilkan HANYA JSON tanpa komentar atau teks tambahan. Pastikan jawabannya sesuai makanan orang indonesia dan sesuai untuk menengah atau kebawah. Pastikan makanan yang mudah diakses dan dimasak dan juga berikan alasan sedikit lebih panjang agar lebih jelas. Fokus pada makanan bergizi untuk mencegah stunting:\n" +
                "{\n" +
                "  \"sarapan\": {\n" +
                "    \"menu\": \"Bubur ayam dengan telur rebus\",\n" +
                "    \"alasan\": \"Tinggi protein, mudah dicerna, mendukung pertumbuhan\"\n" +
                "  },\n" +
                "  \"makanSiang\": {\n" +
                "    \"menu\": \"Nasi merah, ikan goreng, sayur bayam\",\n" +
                "    \"alasan\": \"Kombinasi protein, karbohidrat kompleks, dan zat besi\"\n" +
                "  },\n" +
                "  \"makanMalam\": {\n" +
                "    \"menu\": \"Sop ayam dengan wortel dan kentang\",\n" +
                "    \"alasan\": \"Protein, vitamin A, karbohidrat untuk pertumbuhan\"\n" +
                "  },\n" +
                "  \"totalGizi\": \"Seimbang dengan protein, karbohidrat kompleks, dan mikronutrien\",\n" +
                "  \"biayaPerHari\": \"Rp 30.000 - Rp 40.000\"\n" +
                "}\n\n" +
                "Pastikan JSON valid, fokus pada nutrisi anti-stunting, bahan murah dan bergizi.";
    }

    @PostMapping("/chatbot/saran-makanan")
    public ResponseEntity<Map<String, Object>> getNutritionalSuggestion() {
        String prompt = generateNutritionalPrompt();

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest("gpt-4o", prompt);

        ChatControllerResponse response = restTemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                chatCompletionRequest,
                ChatControllerResponse.class
        );

        if (response != null && !response.getChoices().isEmpty()) {
            String rawOutput = response.getChoices().get(0).getMessage().getContent();

            // Tambahkan pembersihan output
            String cleanedOutput = rawOutput
                    .replaceAll("^```json?\\s*", "") // Hapus backticks json di awal
                    .replaceAll("```$", "")         // Hapus backticks di akhir
                    .trim();                        // Hapus whitespace tambahan

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                Map<String, Object> nutritionalSuggestion = objectMapper.readValue(cleanedOutput, new TypeReference<Map<String, Object>>(){});

                return ResponseEntity.ok(nutritionalSuggestion);
            } catch (Exception e) {
                // Log error untuk debugging
                System.err.println("Cleaned Output: " + cleanedOutput);
                e.printStackTrace();

                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Gagal memproses saran makanan");
                errorResponse.put("errorDetails", e.getMessage());
                errorResponse.put("rawOutput", rawOutput);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Tidak dapat menghasilkan saran makanan");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @PostMapping("/chatbot/olah-bahan")
    public ResponseEntity<Map<String, Object>> getCookingSuggestion(@RequestBody Map<String, String> request) {
        String ingredients = request.get("ingredients");

        String prompt = generateCookingPrompt(ingredients);

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest("gpt-4o", prompt);

        ChatControllerResponse response = restTemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                chatCompletionRequest,
                ChatControllerResponse.class
        );

        // Memeriksa apakah respons tersedia dan memprosesnya
        if (response != null && !response.getChoices().isEmpty()) {
            String rawOutput = response.getChoices().get(0).getMessage().getContent();

            // Pembersihan output jika diperlukan
            String cleanedOutput = rawOutput
                    .replaceAll("^```json?\\s*", "") // Menghapus backticks jika ada
                    .replaceAll("```$", "")         // Menghapus backticks di akhir
                    .trim();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> jsonResponse = objectMapper.readValue(cleanedOutput, new TypeReference<Map<String, Object>>() {});

                return ResponseEntity.ok(jsonResponse);
            } catch (Exception e) {
                // Logging dan handling error
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Gagal memproses saran masakan");
                errorResponse.put("details", e.getMessage());
                errorResponse.put("rawOutput", rawOutput);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Tidak ada respons dari API OpenAI");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private String generateCookingPrompt(String ingredients) {
        String[] ingredientArray = ingredients.split(", ");
        StringBuilder promptBuilder = new StringBuilder();

        promptBuilder.append("{\n");
        promptBuilder.append("  \"instruction\": \"Buatkan saya saran masakan dari bahan berikut:\",\n");
        promptBuilder.append("  \"ingredients\": [\n");
        for (int i = 0; i < ingredientArray.length; i++) {
            promptBuilder.append("    \"").append(ingredientArray[i]).append("\"");
            if (i < ingredientArray.length - 1) {
                promptBuilder.append(",");
            }
            promptBuilder.append("\n");
        }
        promptBuilder.append("  ],\n");
        promptBuilder.append("  \"requirements\": {\n");
        promptBuilder.append("    \"format\": \"JSON\",\n");
        promptBuilder.append("    \"focus\": \"Makanan sehat, mudah dibuat, dan cocok untuk kalangan menengah ke bawah\",\n");
        promptBuilder.append("    \"output\": {\n");
        promptBuilder.append("      \"menu\": \"Nama masakan\",\n");
        promptBuilder.append("      \"steps\": \"Daftar langkah-langkah memasak\",\n");
        promptBuilder.append("      \"reason\": \"Penjelasan singkat mengapa masakan ini sehat dan relevan\"\n");
        promptBuilder.append("    }\n");
        promptBuilder.append("  }\n");
        promptBuilder.append("}");

        return promptBuilder.toString();
    }
}
