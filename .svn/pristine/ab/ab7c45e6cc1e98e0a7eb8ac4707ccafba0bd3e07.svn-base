package com.news.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符集帮助类
 */
@Slf4j
public class CharsetUtil {

	public static final String META_TITLE_REG = "charset=\"?(.+?)\"?\\s?/?>";

	public static final String META_BEGIN_TAG = "<meta";
	public static final String HEAD_END_TAG = "</head>";

	/**
	 * @param url     探测url
	 * @param charset 默认字符编码
	 * @return
	 * @throws IOException
	 */
	public static String detectorUrlEncode(String url, Charset charset) throws IOException {
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = null;
		InputStream inputStream = null;
		BufferedReader bf = null;
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			// 1. 根据response返回content-type获取编码
			response = client.execute(get);
			Header encoding = response.getEntity().getContentEncoding();
			if (Objects.nonNull(encoding)) {
				return encoding.getValue();
			}

			// 2. 根据源代码meta标签获取编码
			inputStream = response.getEntity().getContent();
			bf = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = bf.readLine()) != null) {
				if (line.toLowerCase().contains(META_BEGIN_TAG)) {
					Pattern pattern = Pattern.compile(META_TITLE_REG);
					Matcher matcher = pattern.matcher(line);
					if (matcher.find()) {
						String group = matcher.group(1);
						return group;
					}
				}
				if (line.toLowerCase().contains(HEAD_END_TAG)) {
					break;
				}
			}
		} finally {
			if (response != null) {
				response.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (bf != null) {
				bf.close();
			}
		}
		if (log.isDebugEnabled()) {
			System.out.println("当前url字符集未检测到");
		}
		return charset.name();
	}

	public static void main(String[] args) throws IOException {
		List<String> urls = Files.readAllLines(Paths.get("testUrl.txt"));
		for (String url : urls) {
			System.out.println(detectorUrlEncode(url, CharsetUtils.get("utf-8")));
		}
	}
}
