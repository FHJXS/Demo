package com.news.spider.parse.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.news.dto.DataSourceDTO;
import com.news.entity.Article;
import com.news.spider.parse.handler.IParseHandler;

/**
 * 知乎源码解析处理
 * 
 * @author wayn
 *
 */
public class ZhihuParseHandler implements IParseHandler {

	@Override
	public List<Article> parseHandler(DataSourceDTO dataSourceDTO) {
		// todo
		List<Article> arts = new ArrayList<>();
		return arts;
	}
}
