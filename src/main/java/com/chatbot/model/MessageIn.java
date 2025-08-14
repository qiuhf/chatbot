/*
 * Copyright (c) 2025-2025. the original author or authors.
 */

package com.chatbot.model;

import lombok.Data;

import java.util.Map;

/**
 * Message Input
 *
 * @author Fred
 * @since 2025/8/11
 */
@Data
public class MessageIn {

	private String question;

	private Map<String, Object> history;

}
