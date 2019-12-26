package com.news.spider.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.news.entity.Article;
import com.news.service.ArticleService;
import com.news.spider.constant.Constant;
import com.news.spider.persistence.IPersistenceService;

@Component
public class PersistenceServiceImpl implements IPersistenceService {

	@Resource(name = "redisTemplate")
	private ValueOperations<String, Article> valueOps;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	@Autowired
	private ArticleService articleService;

	@Override
	public void persistence() {
		Long size = listOps.size(Constant.LIST_KEY);
		List<Article> articles = new ArrayList<Article>();
		if (size > 40) {
			for (int i = 0; i < 40; i++) {
				String key = listOps.rightPop(Constant.LIST_KEY);
				// key不存在则跳过
				if (!valueOps.getOperations().hasKey(key)) {
					continue;
				}
				Article article = valueOps.get(String.valueOf(key));
				articles.add(article);
			}
		}
		if (articles.size() > 0) {
			articleService.saveBatch(articles);
		}
	}

}
