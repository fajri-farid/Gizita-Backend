package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Chat_DB")
@NoArgsConstructor
@AllArgsConstructor

public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USER_MESSAGE")
    private String userMessage;

    @Column(name = "BOT_RESPONSE")
    private String botResponse;
}
