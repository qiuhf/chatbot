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
 *  Stores user feedback on AI responses
 * </pre>
 *
 * @author Fred
 * @since 2025/8/11
 */
@Data
@TableName("feedbacks")
public class FeedbacksEntity {

	/**
	 * Auto-incrementing primary key
	 */
	@TableId(type = IdType.AUTO)
	private long id;

	/**
	 * References assistant message being rated
	 */
	private long messageId;

	/**
	 * Numeric rating (1-5 scale)
	 */
	private short rating;

	/**
	 * Optional qualitative feedback text
	 */
	private String comment;

	/**
	 * Timestamp when feedback was submitted
	 */
	private Timestamp createdAt;

	/**
	 * Timestamp when feedback was updated
	 */
	private Timestamp updatedAt;

}
