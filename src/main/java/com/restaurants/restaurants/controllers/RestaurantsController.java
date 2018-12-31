package com.restaurants.restaurants.controllers;

import com.restaurants.restaurants.bo.RestaurantsBO;
import com.restaurants.restaurants.utils.Constants;
import com.restaurants.restaurants.vo.APIServiceVO;
import com.restaurants.restaurants.vo.ResponseVO;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/restaurants")

public class RestaurantsController  {

	private static final Logger logger = Logger.getLogger(RestaurantsController.class);

	/**
	 * ID 		: PIR_001_6695<p>
	 * Path 	: /iam/register<p>
	 * Type 	: POST<p>
	 * Consumes : application/json<p>
	 * Produces : application/json<p>
	 */
	@PostMapping(value = "/register",
			consumes = Constants.APPLICATION_JSON_VALUE,
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> register(HttpEntity<RestaurantsBO> httpEntity) {

//		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "POST /iam/register");
//		try {
//
//
//		} catch (Exception e) {
//			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
//		}
		return null;
	}

}
