package com.news.domain;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.news.filters.TokenFilter;

@Configuration
public class JwtCfg {

	@Bean
	public FilterRegistrationBean<TokenFilter> jwtFilter() {
		final FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<TokenFilter>();
		registrationBean.setFilter(new TokenFilter());
		registrationBean.addUrlPatterns("/api/user/loginToken/*");
		return registrationBean;
	}

}
