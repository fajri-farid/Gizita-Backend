package com.example.demo.Service.ArticleService.Create;

import com.example.demo.Entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.ArticleRepository;

import java.time.LocalDateTime;

@Service
public class CreateArticleServiceImpl implements CreateArticleService{
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article createArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        return articleRepository.save(article);
    }
}
