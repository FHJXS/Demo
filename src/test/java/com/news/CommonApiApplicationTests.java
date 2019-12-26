package com.news;

import static org.junit.Assert.assertNotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.CharsetUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.news.config.news.NewsConfig;
import com.news.dto.DataSourceDTO;
import com.news.entity.Article;
import com.news.service.ArticleService;
import com.news.spider.download.IDownLoadService;
import com.news.utils.CharsetUtil;
import com.news.utils.HttpClientUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonApiApplicationTests {

	@Autowired
	private IDownLoadService downLoadService;

	@Autowired
	private NewsConfig config;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private ArticleService articleService;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, Article> valueOps;

	/**
	 * charset测试
	 * 
	 * @throws IOException
	 */
	@Test
	public void charsetDetetorUtilTest() throws IOException {
		List<String> urls = Files.readAllLines(Paths.get("testUrl.txt"));
		for (String url : urls) {
			System.out.println(CharsetUtil.detectorUrlEncode(url, CharsetUtils.get("utf-8")));
		}
	}

	@Test
	public void httpClientParseTest() throws UnsupportedEncodingException, IOException {
		String string = HttpClientUtil.doGet("http://toutiao.com", CharsetUtils.get("utf-8"));
		System.out.println(string);
	}

	@Test
	public void htmlUnitParseTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnScriptError(false);// 当JS执行出错的时候是否抛出异常, 这里选择不需要
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);// 当HTTP的状态非200时是否抛出异常, 这里选择不需要
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setCssEnabled(false);// 是否启用CSS, 因为不需要展现页面, 所以不需要启用
		webClient.getOptions().setJavaScriptEnabled(true); // 很重要，启用JS
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());// 很重要，设置支持AJAX
		webClient.getOptions().setTimeout(10000);// 设置“浏览器”的请求超时时间
		webClient.setJavaScriptTimeout(10000);// 设置JS执行的超时时间
		HtmlPage htmlPage = null;
		try (FileOutputStream fileOutputStream = new FileOutputStream("D:toutiao1.txt")) {
			htmlPage = webClient.getPage("https://www.toutiao.com/ch/news_hot/");
			webClient.waitForBackgroundJavaScript(30000);// 异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
			String htmlString = htmlPage.asXml();
			fileOutputStream.write(htmlString.getBytes());
			fileOutputStream.flush();
		} finally {
			webClient.close();
		}
	}

	@Test
	public void webDriverParseTest() throws InterruptedException, IOException {
		long time = System.currentTimeMillis();
		// 可省略，若驱动放在其他目录需指定驱动路径
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		ChromeDriver driver = new ChromeDriver(chromeOptions);
		driver.get("https://www.toutiao.com/ch/news_hot/");
		// 休眠1s,为了让js执行完
		String script = StringUtils.join(Files.readAllLines(Paths.get("script.js")), "\n");
		driver.executeScript(script);
		Thread.sleep(5000l);
		// 网页源码
		String source = driver.getPageSource();
		OutputStream os = new FileOutputStream("D:toutiao.txt");
		os.write(source.getBytes());
		os.flush();
		os.close();
		driver.close();
		System.out.println("耗时:" + (System.currentTimeMillis() - time));
	}

	@Test
	public void toutiaoParseTest() {
		// 可省略，若驱动放在其他目录需指定驱动路径
		System.setProperty("webdriver.chrome.driver", "D:chromedriver.exe");
		try {
			DataSourceDTO dataSourceDTO = downLoadService.download(config.getSpiderUrl());
			assertNotNull(dataSourceDTO.getDataSourceContent());
			Document document = Jsoup.parse(dataSourceDTO.getDataSourceContent());
			Elements elements = document.select(".container .index-content .feedBox ul li");
			elements.remove(elements.size() - 1);
			List<Article> arts = new ArrayList<>();
			elements.forEach(item -> {
				String title = item.select(".title-box a").text();
				String href = item.select(".title-box a").first().attr("href");
				String source1 = item.select(".footer div div a").get(1).text();
				String beforeTime = item.select(".footer div span").first().text();
				Date createTime;
				if ("刚刚".equals(beforeTime)) {
					createTime = new Date();
				} else {
					LocalDateTime dateTime;
					if (beforeTime.contains("分钟前")) {
						beforeTime = StringUtils.substringBefore(beforeTime, "分钟前");
						dateTime = LocalDateTime.now().minus(Long.valueOf(beforeTime), ChronoUnit.MINUTES);
					} else {
						beforeTime = StringUtils.substringBefore(beforeTime, "小时前");
						dateTime = LocalDateTime.now().minus(Long.valueOf(beforeTime), ChronoUnit.HOURS);
					}
					ZoneId zoneId = ZoneId.systemDefault();
					ZonedDateTime zdt = dateTime.atZone(zoneId);
					createTime = Date.from(zdt.toInstant());
				}
				String imageUrl = item.select(".lbox a img").attr("src");
				Article article = Article.builder().title(title).href(href).source(source1).createTime(createTime)
						.imageUrl(imageUrl).build();
				arts.add(article);
			});
			List<String> collect = arts.stream().map(item -> JSON.toJSONString(item)).collect(Collectors.toList());
			redisTemplate.opsForList().leftPushAll("toutiaoList", collect);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void newsYouthParseTest() {

	}

	@Test
	public void saveBatch() {
		articleService.saveBatch(Arrays.asList(Article.builder().title("xixi").build()));
	}

	@Test
	public void redisTest() {
		valueOps.set("artcle", Article.builder().title("name").source("xixi").build());
		Article article = valueOps.get("artcle");
		System.out.println(article);
	}
}
