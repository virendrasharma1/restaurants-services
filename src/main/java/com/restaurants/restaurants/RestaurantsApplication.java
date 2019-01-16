package com.restaurants.restaurants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ComponentScan("com.restaurants.restaurants")
@PropertySource("classpath:hibernate.properties")
@PropertySource("classpath:log4j.properties")
@CrossOrigin(origins = "http://localhost:4200")
public class RestaurantsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RestaurantsApplication.class, args);


	}

}

