package com.example.demo.openai;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/chatbot/olah-bahan")
    public String getCookingSuggestion(@RequestBody Map<String, String> request) {
        String ingredients = request.get("ingredients");

        String prompt = generateCookingPrompt(ingredients);

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest("gpt-4o", prompt);

        ChatControllerResponse response = restTemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                chatCompletionRequest,
                ChatControllerResponse.class
        );

        // Memeriksa apakah respons tersedia dan mengembalikan hasil
        if (response != null && !response.getChoices().isEmpty()) {
            return response.getChoices().get(0).getMessage().getContent();
        }

        return "No response from OpenAI API";
    }

    private String generateCookingPrompt(String ingredients) {
        String[] ingredientArray = ingredients.split(", ");
        StringBuilder promptBuilder = new StringBuilder("Saya memiliki bahan makanan berikut: ");
        for (String ingredient : ingredientArray) {
            promptBuilder.append(ingredient).append(", ");
        }
        promptBuilder.setLength(promptBuilder.length() - 2);

        promptBuilder.append(". Buatkan saya saran masakan yang bisa diolah dari bahan-bahan tersebut, sertakan langkah-langkah memasak yang lebih detail, dan pastikan hasilnya relevan dengan bahan yang diberikan dan merupakan makanan yang sehat (pastikan step-stepnya itu menghasilkan makanan yang sehat!) dan juga terpenting output resepnya itu makanan untuk kalangan menengah atau kebawah jadi mereka bisa dengan mudah buat resepnya dengan bahan dan kondisi keuangan seadanya. Format respons dalam bentuk JSON, di mana elemen HTML berada dalam properti \"output\". Setiap langkah disusun dalam bentuk list yang mudah dipahami. Contoh format respons:\n" +
                "{\n" +
                "  \"output\": \"<h2>Resep Olahan dari Bahan yang Tersedia</h2>\n" +
                "    <h3><strong>Resep:</strong> Tumis Kangkung dengan Tahu.</h3>\n" +
                "  <ol>\n" +
                "    <li>Potong tahu menjadi dadu kecil. Pisahkan daun kangkung dari batangnya, kemudian cuci bersih kedua bahan tersebut.</li>\n" +
                "    <li>Iris tipis bawang putih.</li>\n" +
                "    <li>Panaskan sedikit minyak zaitun dalam wajan di atas api sedang. Minyak zaitun dipilih karena lebih sehat.</li>\n" +
                "    <li>Masukkan bawang putih yang sudah diiris tipis dan tumis sebentar hingga harum, jangan sampai gosong.</li>\n" +
                "    <li>Tambahkan tahu dadu ke dalam wajan dan tumis hingga tahu berwarna keemasan dan mulai renyah.</li>\n" +
                "    <li>Masukkan kangkung ke dalam wajan, aduk rata bersama tahu dan bawang putih. Tumis hingga kangkung layu.</li>\n" +
                "    <li>Tambahkan sedikit garam dan merica secukupnya. Jika diinginkan, tambahkan sedikit kaldu jamur untuk meningkatkan rasanya.</li>\n" +
                "    <li>Aduk rata semua bahan, kemudian angkat dari api. Pastikan untuk tidak overcooked agar nutrisi dari kangkung tetap terjaga.</li>\n" +
                "    <li>Sajikan panas sebagai pendamping nasi merah atau nasi putih untuk makanan yang lebih sehat.</li>\n" +
                "  </ol>\n" +
                "  <p><strong>Alasan:</strong> Tumis kangkung dengan tahu adalah pilihan makanan yang sehat. Kangkung kaya akan vitamin A, vitamin C, dan zat besi, sementara tahu menyediakan sumber protein nabati yang penting. Penggunaan minyak zaitun juga menambah asupan lemak sehat.</p>\"\n" +
                "}");
        return promptBuilder.toString();
    }
}
