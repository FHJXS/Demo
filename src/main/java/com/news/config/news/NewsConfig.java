package com.news.config.news;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * news����
 */
@Data
@Configuration
@ConfigurationProperties("news")
public class NewsConfig {

	/**
	 * �ɼ�url
	 */
	private String spiderUrl;

	/**
	 * Ĭ�Ϲ���ʱ�� ��λ:Сʱ
	 */
	private Integer expireTime;
}
