/*
 * Copyright (c) 2025-2025. the original author or authors.
 */

package com.chatbot.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * DateTimeUtil
 *
 * @author Fred
 * @since 2025/8/10
 */
public class DateTimeUtil {

	private DateTimeUtil() {
	}

	/**
	 * <p>
	 * Get the milliseconds of the current date
	 * </p>
	 * @return <code>Long</code>
	 */
	public static Long getMilliByDateTime() {
		return getMilliByDateTime(LocalDateTime.now());
	}

	/**
	 * <p>
	 * Get milliseconds for a specified date
	 * </p>
	 * @param dateTime specified date
	 * @return <code>Long</code>
	 */
	public static Long getMilliByDateTime(LocalDateTime dateTime) {
		return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * <p>
	 * Get the seconds of the current date
	 * </p>
	 * @return <code>Long</code>
	 */
	public static Long getCurrentSeconds() {
		return getSecondsByDateTime(LocalDateTime.now());
	}

	/**
	 * <p>
	 * Get the seconds of a specified date
	 * </p>
	 * @param dateTime specified date
	 * @return <code>Long</code>
	 */
	public static Long getSecondsByDateTime(LocalDateTime dateTime) {
		return dateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
	}

	/**
	 * <p>
	 * Get the current time
	 * </p>
	 * @param pattern Date format
	 * @return <code>String</code>
	 */
	public static String getCurrentDateTimeStr(String pattern) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * <p>
	 * Convert a string-type date to a LocalDateTime type.
	 * </p>
	 * @param dateTimeStr String type time
	 * @param pattern Date format
	 * @return <code>LocalDateTime</code>
	 */
	public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
		return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * <p>
	 * Convert LocalDateTime type time to string type time
	 * </p>
	 * @param datetime LocalDateTime
	 * @param pattern Date format
	 * @return <code>String</code>
	 */
	public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
		return datetime.format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * <p>
	 * Convert Date to LocalDateTime
	 * </p>
	 * @param date Date and time
	 * @return <code>LocalDateTime</code>
	 */
	public static LocalDateTime convertDateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * <p>
	 * Convert LocalDateTime to Date
	 * </p>
	 * @param datetime Date and time
	 * @return <code>Date</code>
	 */
	public static Date convertLocalDateTimeToDate(LocalDateTime datetime) {
		return Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * <p>
	 * Perform addition operations on the original date and time.
	 * </p>
	 * @param dateTime original Date and time
	 * @param number value
	 * @param unit Unit (date, month, year, hour, minute, second), reference:
	 * {@link ChronoUnit}
	 * @return <code>LocalDateTime</code>
	 */
	public static LocalDateTime plus(LocalDateTime dateTime, long number, TemporalUnit unit) {
		return dateTime.plus(number, unit);
	}

	/**
	 * <p>
	 * Perform a subtraction operation on the original date and time.
	 * </p>
	 * @param dateTime original Date and time
	 * @param number value
	 * @param unit Unit (date, month, year, hour, minute, second), reference:
	 * {@link ChronoUnit}
	 * @return <code>LocalDateTime</code>
	 */
	public static LocalDateTime reduce(LocalDateTime dateTime, long number, TemporalUnit unit) {
		return dateTime.minus(number, unit);
	}

	/**
	 * <p>
	 * Compare whether the specified time is earlier than the current time.
	 * </p>
	 * @param dateTime Date and time
	 * @return <code>boolean</code>
	 */
	public static boolean isBefore(LocalDateTime dateTime) {
		return LocalDateTime.now().isBefore(dateTime);
	}

	/**
	 * <p>
	 * Compare whether the specified time is later than the current time.
	 * </p>
	 * @param dateTime Date and time
	 * @return <code>boolean</code>
	 */
	public static boolean isAfter(LocalDateTime dateTime) {
		return LocalDateTime.now().isAfter(dateTime);
	}

	/**
	 * <p>
	 * Get the difference between two times
	 * </p>
	 * @param startTime start time
	 * @param endTime end time
	 * @param unit Unit (date, month, year, hour, minute, second), reference:
	 * {@link ChronoUnit}
	 * @return <code>long</code>
	 */
	public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit unit) {
		Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
		if (unit == ChronoUnit.YEARS) {
			return period.getYears();
		}
		if (unit == ChronoUnit.MONTHS) {
			return period.getYears() * 12L + period.getMonths();
		}
		return unit.between(startTime, endTime);
	}

}
