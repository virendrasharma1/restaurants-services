package com.restaurants.restaurants;

import com.restaurants.restaurants.controllers.RestaurantsController;
import com.restaurants.restaurants.utils.SystemHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource("classpath:spring-config.xml")
@PropertySource("classpath:hibernate.properties")
public class RestaurantsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RestaurantsApplication.class, args);

	}

}

