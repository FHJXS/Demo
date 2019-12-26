package com.news.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.springframework.util.DigestUtils;

/**
 * http请求并帮助类
 */
public class HttpClientUtil {

	public static String doPost(String url, Charset charset) throws IOException {
		HttpPost post = new HttpPost(url);
		try (CloseableHttpClient client = HttpClients.createDefault();
				CloseableHttpResponse response = client.execute(post)) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, charset);
		}
	}

	public static String doGet(String url, Charset charset) throws IOException {
		HttpGet get = new HttpGet(url);
		try (CloseableHttpClient client = HttpClients.createDefault();
				CloseableHttpResponse response = client.execute(get)) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, charset);
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String str = "新京报.！0--0；l*712321321312";
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(str.getBytes());
		System.out.println(md5DigestAsHex);
		try {
			doGet("http://www.baidu.com", CharsetUtils.get("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
