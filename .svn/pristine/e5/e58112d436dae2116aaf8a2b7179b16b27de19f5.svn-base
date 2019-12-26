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
 * @description 请求日志打印输入
 * @author LiJie
 * @date 2019年7月14日15:03:46
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
			log.info("请求源IP:【{}】,请求URL:【{}】,请求参数:【{}】", ipAddr, url, reqParam);
			result = joinPoint.proceed();
			String respParam = HttpResultParseUtil.postHandle(result);
			log.info("请求源IP:【{}】,请求URL:【{}】,返回参数:【{}】", ipAddr, url, respParam);
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
		log.info("调用方法：{}；返回结果： {}", joinPoint, result);
	}

}
