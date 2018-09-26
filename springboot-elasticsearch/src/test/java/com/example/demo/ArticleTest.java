package com.example.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dal.dao.ArticleRepository;
import com.example.demo.dal.dao.ArticleTemplate;
import com.example.demo.dal.entity.Article;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleTest {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleTemplate articleTemplate;
    @Test
    public void testAdd() {
        Article article = new Article();
        article.setId(3);
        article.setSid("d");
        article.setTitle("java2333");
        article.setUrl("http://baidu.com");
        article.setContent("java");
        articleRepository.save(article);
    }
//    @Test
    public void testList() {
        Iterable<Article> list = articleRepository.findAll();
        for (Article article : list) {
            System.out.println(article.getTitle());
        }
    }
//    @Test
    public void testQuery() {
        Iterable<Article> list = articleRepository.findByTitleContaining("java");
        for (Article article : list) {
            System.out.println(article.getTitle());
        }
    }
    @Test
    public void testQueryByTitle() {
        List<Article> list = articleTemplate.queryByTitle("java");
        for (Article article : list) {
            System.out.println(article.getTitle());
        }
    }
}
