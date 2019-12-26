package com.news.spider.parse.handler.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.news.dto.DataSourceDTO;
import com.news.entity.Article;
import com.news.spider.constant.Constant;
import com.news.spider.parse.handler.IParseHandler;

/**
 * 头条源码解析处理
 * 
 * @author wayn
 *
 */
public class ToutiaoParseHandler implements IParseHandler {

	@Override
	public List<Article> parseHandler(DataSourceDTO dataSourceDTO) {
		Document document = Jsoup.parse(dataSourceDTO.getDataSourceContent());
		Elements elements = document.select(".container .index-content .feedBox ul li");
		List<Article> arts = new ArrayList<>();
		if (CollectionUtils.isEmpty(elements)) {
			return arts;
		}
		// 去掉最后一个无用li
		elements.remove(elements.size() - 1);
		for (Element item : elements) {
			// 跳过刷新按钮
			if ("refresh_item_click".equals(item.attr("ga_event"))) {
				continue;
			}
			String title = item.select(".title-box a").text();
			String href = StringUtils.substringBefore(dataSourceDTO.getDataSourceEnum().getUrl(), "/ch/news_hot/")
					+ item.select(".title-box a").first().attr("href");
			String source1 = StringUtils.substringBefore(item.select(".footer div div a").get(1).text(), "⋅");
			String commentNum = "";
			if (item.select(".footer div div a").size() >= 3) {
				commentNum = StringUtils.substringBefore(item.select(".footer div div a").get(2).text(), "评论");
			}
			commentNum = StringUtils.isNotEmpty(commentNum) ? commentNum : "0";
			String beforeTime = item.select(".footer div span").first().text();
			Date createTime;
			if (Constant.MOMENT_SPILTE.equals(beforeTime)) {
				createTime = new Date();
			} else {
				LocalDateTime dateTime;
				if (beforeTime.contains(Constant.MINUTE_SPILTE)) {
					beforeTime = StringUtils.substringBefore(beforeTime, Constant.MINUTE_SPILTE);
					dateTime = LocalDateTime.now().minus(Long.valueOf(beforeTime), ChronoUnit.MINUTES);
				} else {
					beforeTime = StringUtils.substringBefore(beforeTime, Constant.HOUR_SPILTE);
					dateTime = LocalDateTime.now().minus(Long.valueOf(beforeTime), ChronoUnit.HOURS);
				}
				ZoneId zoneId = ZoneId.systemDefault();
				ZonedDateTime zdt = dateTime.atZone(zoneId);
				createTime = Date.from(zdt.toInstant());
			}
			String imageUrl = item.select(".lbox a img").attr("src");
			Article article = Article.builder().title(title).href(href).source(source1).createTime(createTime)
					.imageUrl(imageUrl).commentNum(Integer.parseInt(commentNum)).build();
			arts.add(article);
		}
		return arts;
	}
}
