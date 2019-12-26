package com.news.spider.download;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.CharsetUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import com.news.dto.DataSourceDTO;
import com.news.utils.CharsetUtil;
import com.news.utils.HttpClientUtil;

@Component
public interface IDownLoadService {
	DataSourceDTO download(String url) throws InterruptedException, IOException;

	/**
	 * 根据webdriver解析动态网页代码
	 * 
	 * @param url
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	default String getSourceByWebDriver(String url) throws InterruptedException, IOException {
		ChromeDriver driver = null;
		try {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			driver = new ChromeDriver(chromeOptions);
			driver.get(url);
			String script = StringUtils.join(Files.readAllLines(Paths.get("script.js")), "\n");
			driver.executeScript(script);
			// 休眠4s,为了让js执行完
			Thread.sleep(4000l);
			// 网页源码
			return driver.getPageSource();
		} finally {
			driver.close();
		}
	}

	/**
	 * 根据httpclient解析静态网页代码
	 * 
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	default String getSourceByHttpClient(String url) throws UnsupportedEncodingException, IOException {
		String encode = CharsetUtil.detectorUrlEncode(url, CharsetUtils.get("UTF-8"));
		return HttpClientUtil.doGet(url, CharsetUtils.get(encode));
	}
}
