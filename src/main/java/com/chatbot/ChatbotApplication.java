/*
 * Copyright (c) 2025-2025. the original author or authors.
 */

package com.chatbot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * ChatbotApplication
 *
 * @author Fred
 * @since 2025/8/10
 */
@SpringBootApplication
@MapperScan("com.chatbot.mapper")
@EnableAspectJAutoProxy
public class ChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}

}
