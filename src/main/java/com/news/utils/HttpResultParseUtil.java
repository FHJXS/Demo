package com.news.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

public class HttpResultParseUtil {
	/**
	 * 入参数据
	 * 
	 * @param joinPoint
	 * @param request
	 * @return
	 */
	public static String preHandle(ProceedingJoinPoint joinPoint, HttpServletRequest request) {
		String reqParam = "";
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method targetMethod = methodSignature.getMethod();
		Annotation[] annotations = targetMethod.getAnnotations();
		for (Annotation annotation : annotations) {
			// 此处可以改成自定义的注解
			if (annotation.annotationType().equals(RequestMapping.class)) {
				reqParam = JSON.toJSONString(request.getParameterMap());
				break;
			}
		}
		return reqParam;
	}

	/**
	 * 返回数据
	 * 
	 * @param retVal
	 * @return
	 * @return
	 */
	public static String postHandle(Object retVal) {
		if (null == retVal) {
			return "";
		}
		return JSON.toJSONString(retVal);
	}

	/**
	 * 获取目标主机的ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
	}
}
