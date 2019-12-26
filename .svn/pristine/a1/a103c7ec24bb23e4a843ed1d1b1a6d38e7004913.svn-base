package com.news.config.news;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * news配置
 */
@Data
@Configuration
@ConfigurationProperties("news")
public class NewsConfig {

	/**
	 * 采集url
	 */
	private String spiderUrl;

	/**
	 * 默认过期时间 单位:小时
	 */
	private Integer expireTime;
}
