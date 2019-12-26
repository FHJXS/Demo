package com.news.spider.download.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.news.dto.DataSourceDTO;
import com.news.enums.DataSourceEnum;
import com.news.spider.constant.Constant;
import com.news.spider.download.IDownLoadService;

@Component
public class DownloadServiceImpl implements IDownLoadService {

	private static final Map<String, DataSourceEnum> map;
	static {
		map = new HashMap<String, DataSourceEnum>();
		DataSourceEnum[] values = DataSourceEnum.values();
		for (DataSourceEnum dataSourceEnum : values) {
			map.put(dataSourceEnum.getUrl(), dataSourceEnum);
		}
	}

	@Override
	public DataSourceDTO download(String url) throws InterruptedException, IOException {
		String source = "";
		DataSourceDTO dataSourceDTO = new DataSourceDTO();
		dataSourceDTO.setDataSourceEnum(map.get(url));
		String downloadhandler = map.get(url).getDownloadhandler();
		if (Constant.DOWNLOAD_HANDLER_WEBDRIVER.equals(downloadhandler)) {
			source = getSourceByWebDriver(url);
		} else {
			source = getSourceByHttpClient(url);
		}
		dataSourceDTO.setDataSourceContent(source);
		return dataSourceDTO;
	}
}
