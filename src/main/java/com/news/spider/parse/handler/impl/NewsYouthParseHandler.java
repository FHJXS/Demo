package com.news.spider.parse.handler.impl;

import java.util.ArrayList;
import java.util.List;

import com.news.dto.DataSourceDTO;
import com.news.entity.Article;
import com.news.spider.parse.handler.IParseHandler;

/**
 * �й�������Դ���������
 * 
 * @author wayn
 *
 */
public class NewsYouthParseHandler implements IParseHandler {

	@Override
	public List<Article> parseHandler(DataSourceDTO dataSourceDTO) {
		// todo
		List<Article> arts = new ArrayList<>();
		return arts;
	}
}
