package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Article")
@NoArgsConstructor
@AllArgsConstructor

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String title;
    private String tumbnailImgUrl;
    private String content;
    private String author;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
