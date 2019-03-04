package com.restaurants.controllers;

import com.restaurants.bo.IdValueParams;
import com.restaurants.bo.NameValuePair;
import com.restaurants.exceptions.RecordNotFoundException;
import com.restaurants.bo.RestaurantsBO;
import com.restaurants.utils.Constants;
import com.restaurants.vo.APIServiceVO;
import com.restaurants.vo.ResponseVO;
import com.restaurants.vo.RestaurantsVO;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@Component
@RestController
@RequestMapping("/restaurants")

public class RestaurantsController extends BaseController{

	private static final Logger logger = Logger.getLogger(RestaurantsController.class);

	/**
	 * ID 		: PIR_001_6695<p>
	 * Path 	: /restaurants/register<p>
	 * Type 	: POST<p>
	 * Consumes : application/json<p>
	 * Produces : application/json<p>
	 */
	@PostMapping(value = "/register",
			consumes = Constants.APPLICATION_JSON_VALUE,
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> register(HttpEntity<RestaurantsBO> httpEntity) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "POST /restaurants/register");
		HttpHeaders headers = httpEntity.getHeaders();
		Long restaurantId = Constants.INVALID_ID;
		try {
			RestaurantsBO bo = (RestaurantsBO) this.getValidatedBody(httpEntity);

			RestaurantsVO vo = restaurantsConverter.getRestaurantsVO(bo);
			vo = restaurantsTransactions.register(vo);
			restaurantId = vo.getRestaurantId();
			apiServiceVO.setPayload(helper.toJSON(restaurantId));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getLoginResponseEntity(logger, apiServiceVO, headers, restaurantId);
	}

	/**
	 * ID 		: PIR_001_1141<p>
	 * Path 	: /restaurants/exists<p>
	 * Type 	: GET<p>
	 * Produces : application/json<p>
	 */
	@GetMapping(value = "/exists",
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> doesExistLoginName(HttpEntity<?> httpEntity, @QueryParam("id") String id) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "GET /restaurants/exists?id=" + id);
		HttpHeaders headers = httpEntity.getHeaders();
		Long loginId = Constants.INVALID_ID;
		try {
			RestaurantsVO vo = restaurantsTransactions.ifAlreadyRegistered(id);
			if (vo == null) { throw new RecordNotFoundException(id); }

				final RestaurantsBO bo = restaurantsConverter.getRestaurantsBO(vo);
				apiServiceVO.setPayload(helper.toJSON(bo));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getLoginResponseEntity(logger, apiServiceVO, headers, loginId);
	}

	/**
	 * ID 		: PIR_001_4176<p>
	 * Path 	: /restaurants/login<p>
	 * Type 	: PUT<p>
	 * Consumes : application/json<p>
	 * Produces : application/json<p>
	 */
	@PutMapping(value = "/login",
			consumes = Constants.APPLICATION_JSON_VALUE,
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> login(HttpEntity<NameValuePair> httpEntity) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "PUT /restaurants/login");
		Long loginId = Constants.INVALID_ID;
		HttpHeaders headers = httpEntity.getHeaders();
		try {
			NameValuePair bo = (NameValuePair) this.getValidatedBody(httpEntity);
			String deviceId = this.getDeviceId(httpEntity.getHeaders());
			String ipAddress = this.getIpAddress(httpEntity.getHeaders());

			loginId = restaurantsTransactions.login(bo.getName(), bo.getValue(), deviceId, ipAddress);
			apiServiceVO.setPayload(helper.toJSON(loginId));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return (loginId == Constants.INVALID_ID) ? this.getBareResponseEntity(logger, apiServiceVO) : this.getLoginResponseEntity(logger, apiServiceVO, headers, loginId);
	}

}
