package com.example.demo.Service.ArticleService.Get;

import com.example.demo.Entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.ArticleRepository;

@Service
public class GetArticleServiceImpl implements GetArticleService{
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article getArticleById(int id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + id));
    }
}
