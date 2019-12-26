package com.news.domain;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.news.dto.ResultData;
import com.news.utils.HttpResultParseUtil;

import lombok.extern.log4j.Log4j2;

/**
 * @description ������־��ӡ����
 * @author LiJie
 * @date 2019��7��14��15:03:46
 */
@Aspect
@Log4j2
@Configuration
public class LogConfig {

	@Pointcut("execution(* com.news.controller.*.*(..))")
	public void controllerPoint() {
	}

	@Before("controllerPoint()")
	public void beforeTest() {
	}

	@After("controllerPoint()")
	public void doAfter() {
	}

	@Around("controllerPoint()")
	public Object doAround(ProceedingJoinPoint joinPoint) {
		Object result = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String ipAddr = HttpResultParseUtil.getRemoteHost(request);
			String url = request.getRequestURL().toString();
			String reqParam = HttpResultParseUtil.preHandle(joinPoint, request);
			log.info("����ԴIP:��{}��,����URL:��{}��,�������:��{}��", ipAddr, url, reqParam);
			result = joinPoint.proceed();
			String respParam = HttpResultParseUtil.postHandle(result);
			log.info("����ԴIP:��{}��,����URL:��{}��,���ز���:��{}��", ipAddr, url, respParam);
		} catch (Exception e) {
			log.error(e);
			return ResultData.failed(e.getMessage());
		} catch (Throwable e) {
			log.error(e);
			return ResultData.failed(e.getMessage());
		}
		return result;
	}

	@AfterReturning(value = "controllerPoint()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		log.info("���÷�����{}�����ؽ���� {}", joinPoint, result);
	}

}
