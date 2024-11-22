package com.example.demo.Service.ArticleService.Delete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Repository.ArticleRepository;

@Service
public class DeleteArticleServiceImpl implements DeleteArticleService{
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void deleteArticleById(int id) {
        if (!articleRepository.existsById(id)) {
            throw new RuntimeException("Article not found with ID: " + id);
        }
        articleRepository.deleteById(id);
    }
}
