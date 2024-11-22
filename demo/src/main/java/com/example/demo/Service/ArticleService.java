//package com.example.demo.Service;
//
//import com.example.demo.Entity.Article;
//import com.example.demo.Repository.ArticleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class ArticleService {
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    public Article createArticle(Article article){
//        article.setCreatedAt(LocalDateTime.now());
//        return articleRepository.save(article);
//    }
//
//    public Iterable<Article> getAllArticles() {
//        return articleRepository.findAll();
//    }
//
//    public Article getArticleById(int id) {
//        return articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found with ID: " + id));
//    }
//
//    public Article updateArticleById(int id, Article updatedArticle) {
//        Article existingArticle = articleRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Article not found with ID: " + id));
//
//        existingArticle.setTitle(updatedArticle.getTitle());
//        existingArticle.setTumbnailImgUrl(updatedArticle.getTumbnailImgUrl());
//        existingArticle.setContent(updatedArticle.getContent());
//
//        return articleRepository.save(existingArticle);
//    }
//
//    public void deleteArticleById(int id) {
//        if (!articleRepository.existsById(id)) {
//            throw new RuntimeException("Article not found with ID: " + id);
//        }
//        articleRepository.deleteById(id);
//    }
//}
