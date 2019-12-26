package com.news;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.news.dao")
@EnableSwagger2
public class CommonApiApplication {

	public static void main(String[] args) {

		// 放在项目同一目录下可省略，若驱动放在其他目录需指定驱动路径
//		System.setProperty("webdriver.chrome.driver", "D:chromedriver.exe");
		SpringApplication.run(CommonApiApplication.class, args);
	}

}
