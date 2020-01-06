package com.springboot.servicioAtm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	
	
	@Bean
	public WebClient registrarWebClient() {

		return WebClient.create("http://localhost:8001/api/personal");
		
		//return WebClient.create("http://gateway-server:9090/api/personal");

	}
	
}
