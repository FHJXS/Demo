package com.news.spider.parse.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.news.config.news.NewsConfig;
import com.news.dto.DataSourceDTO;
import com.news.entity.Article;
import com.news.spider.constant.Constant;
import com.news.spider.parse.IHtmlParseService;
import com.news.spider.parse.handler.IParseHandler;
import com.news.utils.MD5Util;

@Component
public class HtmlParseServiceImpl implements IHtmlParseService {

	@Resource(name = "redisTemplate")
	private ValueOperations<String, Article> valueOps;

	@Resource(name = "redisTemplate")
	private ListOperations<String, String> listOps;

	@Autowired
	private NewsConfig config;

	@Override
	@SuppressWarnings("unchecked")
	public void htmlParse(DataSourceDTO dataSourceDTO)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<IParseHandler> clazz = (Class<IParseHandler>) Class
				.forName(dataSourceDTO.getDataSourceEnum().getClassPath());
		IParseHandler parseHandler = clazz.newInstance();
		for (Article article : parseHandler.parseHandler(dataSourceDTO)) {
			String key = MD5Util.md5(article.getTitle());
			synchronized (config) {
				// 设置去重
				if (!valueOps.getOperations().hasKey(Constant.VALUE_KEY + key)) {
					// 不重复记录放进redis中，默认6小时过期
					valueOps.set(Constant.VALUE_KEY + key, article, config.getExpireTime(), TimeUnit.HOURS);
					listOps.leftPush(Constant.LIST_KEY, Constant.VALUE_KEY + key);
				}
			}
		}
	}
}
