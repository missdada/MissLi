package com.example.demo.dal.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.dal.entity.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    List<Article> findByTitleContaining(String title);
}
