/*
 * Copyright (c) 2025-2025. the original author or authors.
 */

package com.chatbot.configration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Agent Type
 *
 * @author Fred
 * @since 2025/8/10
 */
@Getter
public enum AgentType {

	/**
	 * Air quality expert
	 */
	AIR_QUALITY("Air quality expert", "Answer questions about air quality PM25, PM10, SO2, CO, O3, NO2, or HC issues"),
	/**
	 * Meteorological expert
	 */
	WEATHER("Meteorological expert",
			"answering questions about wind direction, rainfall, humidity, temperature, atmospheric pressure, solar radiation, rainfall intensity, and other meteorological issues."),
	/**
	 * UV protection expert
	 */
	UV_RADIATION("UV protection expert", "analyze UV index, sunscreen recommendations, and sun exposure risks"),
	/**
	 * Health Advisor
	 */
	HEALTH_ADVICE("Health Advisor", "Providing protection advice for sensitive groups and health risk warnings"),
	/**
	 * Environmental Science Assistant
	 */
	GENERAL("Environmental Science Assistant",
			"Answering basic environmental science questions and other comprehensive questions");

	final String title;

	final String description;

	AgentType(String title, String description) {
		this.title = title;
		this.description = description;
	}

}
