package com.example.demo.Service.ArticleService.Update;

import com.example.demo.Entity.Article;
import org.springframework.stereotype.Service;

@Service
public interface UpdateArticleService {
    Article updateArticleById(int id, Article updatedArticle);
}
