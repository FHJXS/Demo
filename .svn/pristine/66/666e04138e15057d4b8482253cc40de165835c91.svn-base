package com.news.spider.schedulemanager;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.news.config.news.NewsConfig;
import com.news.dto.DataSourceDTO;
import com.news.spider.download.IDownLoadService;
import com.news.spider.parse.IHtmlParseService;
import com.news.spider.persistence.IPersistenceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class ScheduleTask {

	@Autowired
	private IDownLoadService downLoadService;

	@Autowired
	private IHtmlParseService htmlParseService;

	@Autowired
	private IPersistenceService iPersistenceService;

	@Autowired
	private NewsConfig config;

	/**
	 * 解析网页源代码，持久化采集对象到redis
	 */
	@Async
	@Scheduled(cron = "0 0/1 * * * ?")
	public void htmlParseTask() {
		log.info("------------------------------- 解析头条源代码任务，begin-------------------------------------");
		CountDownLatch begin = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(5);
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(() -> {
				try {
					begin.await();
					String[] split = config.getSpiderUrl().split(",");
					for (String url : split) {
						DataSourceDTO dataSourceDTO = downLoadService.download(url);
						assertNotNull(dataSourceDTO.getDataSourceContent());
						htmlParseService.htmlParse(dataSourceDTO);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.info("------------------------------- 解析头条源代码线程{}，失败:{}", Thread.currentThread().getName(),
							e.getMessage());
				} finally {
					end.countDown();
				}
			});
			executorService.execute(thread);
		}
		begin.countDown();
		try {
			end.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
		log.info("------------------------------- 解析头条源代码任务，end-------------------------------------");
	}

	/**
	 * 将redis的采集对象持久化到数据库中
	 */
	@Async
	@Scheduled(cron = "30 0/2 * * * ?")
	public void persistenceTask() {
		log.info("------------------------------- 持久化采集对象，begin-------------------------------------");
		try {
			iPersistenceService.persistence();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("------------------------------- 持久化采集对象，失败：{}", e.getMessage());
		}
		log.info("------------------------------- 持久化采集对象，end-------------------------------------");
	}

}
