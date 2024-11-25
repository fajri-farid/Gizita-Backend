package com.example.demo.Controller;

import com.example.demo.Entity.ChatMessage;
import com.example.demo.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestBody String userMessage) {
        try {
            // Memanggil service untuk mendapatkan respons chatbot
            ChatMessage chatMessage = chatService.getChatResponse(userMessage);
            return ResponseEntity.ok(chatMessage);  // Mengembalikan ChatMessage sebagai respons HTTP 200
        } catch (IOException e) {
            // Menangkap exception dan mengembalikan pesan error yang lebih rinci
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }
}
