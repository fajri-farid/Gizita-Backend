package com.example.demo.Controller;

import com.example.demo.Entity.Article;
import com.example.demo.Model.ResponseDTO;
import com.example.demo.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/article")
    public ResponseEntity<ResponseDTO<?>> postArticle(@RequestBody Article article){
        try {
            Article savedArticle = articleService.createArticle(article);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Article created successfully", savedArticle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, "Failed to create article: " + e.getMessage(), null));
        }
    }

    @GetMapping("/articles")
    public ResponseEntity<ResponseDTO<?>> getAllArticles() {
        try {
            Iterable<Article> articles = articleService.getAllArticles();
            return ResponseEntity.ok(new ResponseDTO<>(true, "Articles retrieved successfully", articles));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, "Failed to retrieve articles: " + e.getMessage(), null));
        }
    }

    @GetMapping("/article/{id}")
    public ResponseEntity<ResponseDTO<?>> getArticleById(@PathVariable int id) {
        try {
            Article article = articleService.getArticleById(id);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Article retrieved successfully", article));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, "Failed to retrieve article: " + e.getMessage(), null));
        }
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<ResponseDTO<?>> updateArticleById(@PathVariable int id, @RequestBody Article updatedArticle) {
        try {
            Article article = articleService.updateArticleById(id, updatedArticle);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Article updated successfully", article));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, "Failed to update article: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<ResponseDTO<?>> deleteArticleById(@PathVariable int id) {
        try {
            articleService.deleteArticleById(id);
            return ResponseEntity.ok(new ResponseDTO<>(true, "Article deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO<>(false, "Failed to delete article: " + e.getMessage(), null));
        }
    }
}