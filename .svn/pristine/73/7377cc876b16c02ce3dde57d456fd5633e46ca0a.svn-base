package com.news.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.news.utils.JWTUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TokenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setCharacterEncoding("utf-8");
		String token = request.getParameter("token");
		String login_id = request.getParameter("login_id");
		log.info("调用Token登录================>：用户名：【{}】，token：【{}】", login_id, token);
		if (login_id.equals(JWTUtil.unsign(token))) {
			chain.doFilter(request, response);
		} else {
			throw new RuntimeException("Token无效");
		}
	}

}
