/*
 * Copyright (c) 2025-2025. the original author or authors.
 */

package com.chatbot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * AuditLog
 *
 * @author Fred
 * @since 2025/8/10
 */
@Slf4j
@Component
@Aspect
public class AuditLog {

	private static final String START_TIME = "start_time";

	/**
	 * Define entry point expressions
	 */
	@Pointcut("execution(public * com.chatbot.controller.*.*(..))")
	public void action() {
	}

	/**
	 * Notification run before target method execution
	 * @param point JoinPoint
	 */
	@Before("action()")
	public void beforeLog(JoinPoint point) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

		log.debug("[Request IP]: {}", request.getRemoteAddr());
		log.debug("[Request URL]: {}", request.getRequestURL());
		log.debug("[Request class name]: {}", point.getSignature().getDeclaringTypeName());
		log.debug("[Request mothed name]: {}", point.getSignature().getName());
		log.debug("[Request parameters]: {}，", request.getParameterMap());

		request.setAttribute(START_TIME, System.currentTimeMillis());
	}

	/**
	 * Notification that runs after the target method has been successfully executed and
	 * returned results.
	 */
	@AfterReturning("action()")
	public void afterReturning() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

		log.debug("【Request duration】: {} ms", System.currentTimeMillis() - (Long) request.getAttribute(START_TIME));

		log.debug("【User-Agent】：{}", request.getHeader("User-Agent"));
	}

}
