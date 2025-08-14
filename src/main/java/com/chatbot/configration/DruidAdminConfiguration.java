/*
 * Copyright (c) 2025-2025. the original author or authors.
 */

package com.chatbot.configration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Druid Admin Configuration
 *
 * @author Fred
 * @since 2025/8/13
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true",
		matchIfMissing = true)
public class DruidAdminConfiguration {

	@Bean
	public FilterRegistrationBean<Filter> removeDruidAdFilterRegistrationBean(DruidStatProperties properties) {
		// Obtain parameters for the Druid monitoring page
		DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
		String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
		String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");

		Filter filter = new Filter() {
			@Override
			public void init(FilterConfig filterConfig) {
			}

			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
				chain.doFilter(request, response);
				// Reset response to avoid duplicate writes
				response.resetBuffer();
				// Obtain common.js
				String text = Utils.readFromResource("support/http/resources/js/common.js");
				// Replace the content in common.js to make it compatible with Spring Boot
				// Admin.
				text = text.replaceAll("<a.*?banner\"></a><br/>", "");
				text = text.replaceAll("parent.document.title = .*?\n", "");
				response.getWriter().write(text);
			}

			@Override
			public void destroy() {
			}
		};

		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns(commonJsPattern);
		return registrationBean;
	}

}
