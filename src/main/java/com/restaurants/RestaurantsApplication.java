package com.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@ComponentScan("com.restaurants")
@PropertySource("classpath:hibernate.properties")
@PropertySource("classpath:log4j.properties")
public class RestaurantsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RestaurantsApplication.class, args);
	}
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setExposedHeaders(Arrays.asList("Content-Type", "Accept",
				"X-com-restaurant-id", "X-com-auth-token","X-com-device-id","X-com-ip-address", "X-com-latitude","X-com-longitude"));
		config.setAllowedOrigins(Collections.singletonList("*"));
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept","Content-Type", "Accept",
				"X-com-restaurant-id", "X-com-auth-token","X-com-device-id","X-com-ip-address", "X-com-latitude","X-com-longitude"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}

