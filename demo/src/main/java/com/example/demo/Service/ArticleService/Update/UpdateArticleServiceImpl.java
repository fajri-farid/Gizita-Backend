package com.example.demo.Service.ArticleService.Update;

import com.example.demo.Entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.ArticleRepository;

import java.time.LocalDateTime;

@Service
public class UpdateArticleServiceImpl implements  UpdateArticleService{
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article updateArticleById(int id, Article updatedArticle) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + id));
        existingArticle.setTitle(updatedArticle.getTitle());
        existingArticle.setTumbnailImgUrl(updatedArticle.getTumbnailImgUrl());
        existingArticle.setContent(updatedArticle.getContent());

        existingArticle.setUpdatedAt(LocalDateTime.now());
        return articleRepository.save(existingArticle);
    }
}
