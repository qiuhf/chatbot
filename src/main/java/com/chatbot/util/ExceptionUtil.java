/*
 * Copyright (c) 2025-2025. the original author or authors.
 */

package com.chatbot.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * ExceptionUtil
 *
 * @author Fred
 * @since 2025/8/10
 */
@Slf4j
public class ExceptionUtil {

	private ExceptionUtil() {
	}

	/**
	 * Get exception message
	 * @param e Exception
	 * @return String
	 */
	public static String getMessage(Exception e) {
		try (StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new PrintWriter(stringWriter)) {
			e.printStackTrace(printWriter);
			printWriter.flush();
			stringWriter.flush();
			return stringWriter.toString();
		}
		catch (IOException ex) {
			log.error("ExceptionUtil.getMessage failed. Details:", ex);
		}

		return e.getMessage();
	}

}
