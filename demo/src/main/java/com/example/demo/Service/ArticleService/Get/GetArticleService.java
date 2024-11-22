package com.example.demo.Service.ArticleService.Get;

import com.example.demo.Entity.Article;
import org.springframework.stereotype.Service;

@Service
public interface GetArticleService {
    Iterable<Article> getAllArticles();
    Article getArticleById(int id);
}
