package com.example.demo.dal.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;

import com.example.demo.dal.entity.Article;

@Repository
public class ArticleTemplate {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	public List<Article> queryByTitle(String title) {
		return elasticsearchTemplate.queryForList(new CriteriaQuery(Criteria.where("title").contains(title)),
				Article.class);
	}
}
