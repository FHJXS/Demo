package com.news.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Api访问地址 http://localhost:8080/swagger-ui.html#/
 * @author LiJie
 * @Date 2019年7月14日15:01:15
 */
@Configuration
public class SwaggerTwoConfig {
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.news.controller")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("本项目的API文档").description("要学会使用简单优雅的restful风格来写接口")
				.termsOfServiceUrl("https://github.com/fhjxs").version("1.0").build();
	}
}
