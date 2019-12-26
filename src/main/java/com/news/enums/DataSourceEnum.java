package com.news.enums;

public enum DataSourceEnum {

	TOUTIAO("https://www.toutiao.com/ch/news_hot/", "com.news.spider.parse.handler.impl.ToutiaoParseHandler",
			"webDriver"), // ͷ��
	ZHIHU("https://www.zhihu.com/", "com.news.spider.parse.handler.impl.ZhihuParseHandler", "webDriver"), // ֪��
	QQ("https://www.qq.com/", "com.news.spider.parse.handler.impl.QQParseHandler", "httpClient"), // ��Ѷ��
	NEWS_YOUTH("http://news.youth.cn/gn/", "com.news.spider.parse.handler.impl.NewsYouthParseHandler", "httpClient"); // �й�������

	private String url;
	private String classPath;
	private String downloadhandler;

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDownloadhandler() {
		return downloadhandler;
	}

	public void setDownloadhandler(String downloadhandler) {
		this.downloadhandler = downloadhandler;
	}

	private DataSourceEnum(String url, String classPath, String downloadhandler) {
		this.url = url;
		this.classPath = classPath;
		this.downloadhandler = downloadhandler;
	}

}
