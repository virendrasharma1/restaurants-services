package com.restaurants.controllers;

import com.restaurants.bo.*;
import com.restaurants.exceptions.RecordNotFoundException;
import com.restaurants.utils.Constants;
import com.restaurants.vo.*;
import com.restaurants.vo.Ids.GlobalItemsId;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

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
	 * ID 		: PIR_001_1141<p>
	 * Path 	: /restaurants/all/items/types<p>
	 * Type 	: GET<p>
	 * Produces : application/json<p>
	 */
	@GetMapping(value = "/all/items/types",
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> getAllItemTypes(HttpEntity<?> httpEntity) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "GET /restaurants/all/items/types");
		HttpHeaders headers = httpEntity.getHeaders();
		try {
			List<GlobalItemsVO> vo = restaurantsTransactions.getAllItemTypes();

			List<GlobalItmesBO> bo = restaurantsConverter.getGlobalItemsListBO(vo);

			apiServiceVO.setPayload(helper.toJSON(bo));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getAuthenticatedResponseEntity(logger, apiServiceVO, headers);
	}

	/**
	 * ID 		: PIR_001_1141<p>
	 * Path 	: /restaurants/all/item/names<p>
	 * Type 	: GET<p>
	 * Produces : application/json<p>
	 */
	@GetMapping(value = "/all/item/names",
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> getItemsNames(HttpEntity<?> httpEntity, @QueryParam("type") String type) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "GET /restaurants/all/item/names?type=" + type);
		HttpHeaders headers = httpEntity.getHeaders();
		try {
			List<GlobalItemsVO> vo = restaurantsTransactions.getItemNamesWithGivenType(type);
			List<GlobalItmesBO> bo = restaurantsConverter.getGlobalItemsListBO(vo);
			apiServiceVO.setPayload(helper.toJSON(bo));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getAuthenticatedResponseEntity(logger, apiServiceVO, headers);
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
		Long restaurantId = Constants.INVALID_ID;
		HttpHeaders headers = httpEntity.getHeaders();
		try {
			NameValuePair bo = (NameValuePair) this.getValidatedBody(httpEntity);
			String deviceId = this.getDeviceId(httpEntity.getHeaders());
			String ipAddress = this.getIpAddress(httpEntity.getHeaders());

			restaurantId = restaurantsTransactions.login(bo.getName(), bo.getValue(), deviceId, ipAddress);
			apiServiceVO.setPayload(helper.toJSON(restaurantId));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return (restaurantId == Constants.INVALID_ID) ? this.getBareResponseEntity(logger, apiServiceVO) : this.getLoginResponseEntity(logger, apiServiceVO, headers, restaurantId);
	}

	/**
	 * ID 		: PIR_001_6695<p>
	 * Path 	: /restaurants/item<p>
	 * Type 	: POST<p>
	 * Consumes : application/json<p>
	 * Produces : application/json<p>
	 */
	@PostMapping(value = "/item",
			consumes = Constants.APPLICATION_JSON_VALUE,
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> saveRestaurantItem(HttpEntity<RestaurantItemsBO> httpEntity) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "POST /restaurants/item");
		HttpHeaders headers = httpEntity.getHeaders();
		try {
			RestaurantItemsBO bo = (RestaurantItemsBO) this.getValidatedBody(httpEntity);

			RestaurantItemsVO vo = restaurantsConverter.getRestaurantItem(bo);
			vo = restaurantsTransactions.saveRestaurantItem(vo);
			apiServiceVO.setPayload(helper.toJSON(vo));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getAuthenticatedResponseEntity(logger, apiServiceVO, headers);
	}

	/**
	 * ID 		: PIR_001_1141<p>
	 * Path 	: /restaurants/items/types<p>
	 * Type 	: GET<p>
	 * Produces : application/json<p>
	 */
	@GetMapping(value = "/items/types",
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> getAllItemTypesOfRestaurant(HttpEntity<?> httpEntity) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "GET /restaurants/items/types");
		HttpHeaders headers = httpEntity.getHeaders();
		try {
			Long restaurantId = this.authenticateRequest(httpEntity.getHeaders());
			List<RestaurantItemsVO> vo = restaurantsTransactions.getAllItemTypesOfRestaurant(restaurantId);

			List<RestaurantItemsBO> bo = restaurantsConverter.getRestaurantItemListBO(vo);

			apiServiceVO.setPayload(helper.toJSON(bo));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getAuthenticatedResponseEntity(logger, apiServiceVO, headers);
	}

	/**
	 * ID 		: PIR_001_1141<p>
	 * Path 	: /restaurants/item/names<p>
	 * Type 	: GET<p>
	 * Produces : application/json<p>
	 */
	@GetMapping(value = "/item/names",
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> getItemNamesOfRestaurantOnTheBasisOfType(HttpEntity<?> httpEntity, @QueryParam("type") String type) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "GET /restaurants/item/names?type=" + type);
		HttpHeaders headers = httpEntity.getHeaders();
		try {
			Long restaurantId = this.authenticateRequest(httpEntity.getHeaders());
			List<RestaurantItemsVO> vo = restaurantsTransactions.getItemNamesOfRestaurantOnTheBasisOfType(restaurantId, type);
			List<RestaurantItemsBO> bo = restaurantsConverter.getRestaurantItemListBO(vo);

			apiServiceVO.setPayload(helper.toJSON(bo));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getAuthenticatedResponseEntity(logger, apiServiceVO, headers);
	}

	/**
	 * ID 		: PIR_001_6695<p>
	 * Path 	: /restaurants/order<p>
	 * Type 	: POST<p>
	 * Consumes : application/json<p>
	 * Produces : application/json<p>
	 */
	@PostMapping(value = "/order",
			consumes = Constants.APPLICATION_JSON_VALUE,
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> saveRestaurantOrder(HttpEntity<RestaurantOrdersBO> httpEntity) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "POST /restaurants/order");
		HttpHeaders headers = httpEntity.getHeaders();
		try {
			RestaurantOrdersBO bo = (RestaurantOrdersBO) this.getValidatedBody(httpEntity);

			RestaurantOrdersVO vo = restaurantsConverter.getRestaurantOrderVO(bo);
			vo = restaurantsTransactions.saveRestaurantOrder(vo);
			apiServiceVO.setPayload(helper.toJSON(vo));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getAuthenticatedResponseEntity(logger, apiServiceVO, headers);
	}

	/**
	 * ID 		: PIR_001_1141<p>
	 * Path 	: /restaurants/orders<p>
	 * Type 	: GET<p>
	 * Produces : application/json<p>
	 */
	@GetMapping(value = "/orders",
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> getMyOrders(HttpEntity<?> httpEntity) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "GET /restaurants/orders");
		HttpHeaders headers = httpEntity.getHeaders();
		try {
			Long restaurantId = this.authenticateRequest(httpEntity.getHeaders());
			List<RestaurantOrdersVO> vo = restaurantsTransactions.getRestaurantOrders(restaurantId);
			List<RestaurantOrdersBO> bo = restaurantsConverter.getRestaurantOrderBOList(vo);

			apiServiceVO.setPayload(helper.toJSON(bo));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getAuthenticatedResponseEntity(logger, apiServiceVO, headers);
	}

	/**
	 * ID 		: PIR_001_1363<p>
	 * Path 	: /iam/logout<p>
	 * Type 	: GET<p>
	 * Produces : application/json<p>
	 */
	@GetMapping(value = "/logout",
			produces = Constants.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> logout(HttpEntity<?> httpEntity) {

		APIServiceVO apiServiceVO = this.initializeResponse(logger, httpEntity, "GET /iam/logout");
		try {
			HttpHeaders headers = httpEntity.getHeaders();
			Long loginId = this.authenticateRequest(headers);

			restaurantsTransactions.logout(loginId, this.getDeviceId(headers));

		} catch (Exception e) {
			this.handleAppExceptions(logger, e, apiServiceVO, httpEntity);
		}
		return this.getBareResponseEntity(logger, apiServiceVO);
	}

}
