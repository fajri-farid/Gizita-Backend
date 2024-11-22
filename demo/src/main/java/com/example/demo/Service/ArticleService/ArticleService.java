package com.example.demo.Service.ArticleService;

import com.example.demo.Entity.Article;
import com.example.demo.Service.ArticleService.Create.CreateArticleService;
import com.example.demo.Service.ArticleService.Delete.DeleteArticleService;
import com.example.demo.Service.ArticleService.Get.GetArticleService;
import com.example.demo.Service.ArticleService.Update.UpdateArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired
    private CreateArticleService createArticleService;

    @Autowired
    private UpdateArticleService updateArticleService;

    @Autowired
    private DeleteArticleService deleteArticleService;

    @Autowired
    private GetArticleService getArticleService;

    public Article createArticle(Article article) {
        return createArticleService.createArticle(article);
    }

    public Article updateArticle(int id, Article article) {
        return updateArticleService.updateArticleById(id, article);
    }

    public void deleteArticle(int id) {
        deleteArticleService.deleteArticleById(id);
    }

    public Iterable<Article> getAllArticles() {
        return getArticleService.getAllArticles();
    }

    public Article getArticleById(int id) {
        return getArticleService.getArticleById(id);
    }
}
