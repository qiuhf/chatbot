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
 *     Stores AI conversation sessions
 * </pre>
 *
 * @author Fred
 * @since 2025/8/11
 */
@Data
@TableName("conversations")
public class ConversationsEntity {

	/**
	 * Auto-incrementing primary key
	 */
	@TableId(type = IdType.AUTO)
	private long id;

	/**
	 * User identifier (nullable for anonymous users)
	 */
	private String userId;

	/**
	 * Conversation title/summary (e.g., first message excerpt)
	 */
	private String title;

	/**
	 * Timestamp when conversation was created
	 */
	private Timestamp createdAt;

	/**
	 * Conversation deletion (soft deletion)
	 */
	private boolean deleted;

}
