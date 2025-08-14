/*
 * Copyright (c) 2025-2025. the original author or authors.
 */

package com.chatbot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * <pre>
 *   Stores individual messages in conversations
 * </pre>
 *
 * @author Fred
 * @since 2025/8/11
 */
@Data
@TableName("messages")
public class MessagesEntity {

	/**
	 * Auto-incrementing primary key
	 */
	@TableId(type = IdType.AUTO)
	private long id;

	/**
	 * Associated conversation ID
	 */
	private long conversationId;

	/**
	 * Message origin: 'user' or 'assistant'
	 */
	private String role;

	/**
	 * The actual message text content
	 */
	private String content;

	/**
	 * Timestamp when message was created
	 */
	private Timestamp createdAt;

	/**
	 * AI model identifier used for response
	 */
	private String model;

}
