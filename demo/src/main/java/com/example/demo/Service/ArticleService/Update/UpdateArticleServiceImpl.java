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

        existingArticle.setCategory(updatedArticle.getCategory());
        existingArticle.setIcon(updatedArticle.getIcon());
        existingArticle.setAuthor(updatedArticle.getAuthor());
        existingArticle.setDescription(updatedArticle.getDescription());
        existingArticle.setMonth(updatedArticle.getMonth());
        existingArticle.setViews(updatedArticle.getViews());
        existingArticle.setLikes(updatedArticle.getLikes());
        existingArticle.setImage(updatedArticle.getImage());
        existingArticle.setLink_artikel(updatedArticle.getLink_artikel());

        existingArticle.setUpdatedAt(LocalDateTime.now());
        return articleRepository.save(existingArticle);
    }
}
