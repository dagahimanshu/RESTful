package com.adobe.demo.cfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Value("${address}")
	private String address;
	@Value("${port}")
	private int port;
	
	@Bean
	public EmailConfig getEmailConfig() {
		return new EmailConfig(address, port);
	}
}
