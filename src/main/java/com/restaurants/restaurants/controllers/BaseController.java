package com.restaurants.restaurants.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.restaurants.restaurants.converters.RestaurantsConverter;
import com.restaurants.restaurants.exceptions.*;
import com.restaurants.restaurants.services.interfaces.LoginHistoriesServices;
import com.restaurants.restaurants.services.interfaces.RestaurantsSessionsServices;
import com.restaurants.restaurants.transactions.interfaces.RestaurantsTransactions;
import com.restaurants.restaurants.utils.Constants;
import com.restaurants.restaurants.utils.DataParser;
import com.restaurants.restaurants.utils.SystemHelper;
import com.restaurants.restaurants.vo.APIServiceVO;
import com.restaurants.restaurants.vo.LoginHistoriesVO;
import com.restaurants.restaurants.vo.ResponseVO;
import com.restaurants.restaurants.vo.RestaurantsSessionsVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class BaseController {
	@Autowired protected Validator validator;
	@Autowired protected SystemHelper helper;
	@Autowired protected DataParser dataParser;
	@Autowired protected RestaurantsConverter restaurantsConverter;
	@Autowired protected RestaurantsTransactions restaurantsTransactions;
	@Autowired protected RestaurantsSessionsServices restaurantsSessionsServices;
	@Autowired protected LoginHistoriesServices loginHistoryServices;


	protected APIServiceVO initializeResponseWithoutLog(String message) {
		return new APIServiceVO(message);
	}

	protected APIServiceVO initializeResponse(Logger logger, HttpEntity<?> httpEntity, String message) {
		logger.debug(message);
		if (logger.isDebugEnabled()) {
			logger.debug(helper.toJSON(httpEntity));
		}
		return new APIServiceVO(message);
	}

	private String getCallType(String message) {
		int endIndex = message.indexOf('/', 0);
		message = message.substring(0 , endIndex);
		return message.trim();
	}

	private String getApiUrl(String message) {
		int beginIndex = message.indexOf('/', 0);
		message = message.substring(beginIndex);
		return message.trim();
	}

	protected void logAPIEntry(Logger logger, HttpEntity<?> httpEntity, String message) {
		logger.debug(message);
		if (logger.isDebugEnabled()) {
			logger.debug(helper.toJSON(httpEntity));
		}
	}

	protected String getMessage(String message, String tag, String value) {
		return message.replace(tag, value);
	}

	protected void handleAppExceptions(Logger logger, Exception e, APIServiceVO apiServiceVO, HttpEntity<?> httpEntity) {
		StringBuilder message = new StringBuilder(apiServiceVO.getApiCall() + " Exception Handling :" + Constants.NEXT_LINE);
		if (httpEntity != null) { message.append(helper.toJSON(httpEntity)).append(Constants.NEXT_LINE); }

		if (e instanceof RecordNotFoundException) {
			RecordNotFoundException a = (RecordNotFoundException) e;
			apiServiceVO.setAppStatusCode(Constants.APP_CODE_RECORD_NOT_FOUND);
			logger.debug(message + "RecordNotFoundException : " + a.getMessage());
		} else if (e instanceof DuplicateRecordException) {
			DuplicateRecordException a = (DuplicateRecordException) e;
			apiServiceVO.setAppStatusCode(Constants.APP_CODE_DUPLICATE_RECORD);
			logger.debug(message + "DuplicateRecordException : " + a.getMessage());
		} else if (e instanceof SimpleException) {
			SimpleException a = (SimpleException) e;
			apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
			logger.debug(message + "SimpleException : " + a.getMessage());
		} else if (e instanceof UnAuthorizedException) {
			UnAuthorizedException u = (UnAuthorizedException) e;
			apiServiceVO.setAppStatusCode(Constants.APP_CODE_INVALID_REQUEST);
			logger.debug("UnAuthorizedException : " + u.getMessage());
		} else if (e instanceof UserAuthenticationException) {
			UserAuthenticationException u = (UserAuthenticationException) e;
			apiServiceVO.setAppStatusCode(u.getErrorCode());
			logger.debug("UserAuthenticationException : " + u.getMessage());
		} else {

			if (e instanceof AppErrorException) {
				AppErrorException a = (AppErrorException) e;
				apiServiceVO.setAppStatusCode(a.getErrorCode());
				message.append("AppErrorException : ").append(a.getMessage());
			} else if (e instanceof DataFormatException) {
				DataFormatException d = (DataFormatException) e;
				apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
				message.append("DataFormatException : ").append(d.getMessage());
			}  else if (e instanceof InvalidDateException) {
				apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
				message.append("InvalidDateException : ");
			} else if (e instanceof DataIntegrityViolationException) {
				apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
				message.append("DataIntegrityViolationException : ");
			} else if (e instanceof InputPayloadException) {
				InputPayloadException i = (InputPayloadException) e;
				apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
				message.append("InputPayloadException : ");
			} else if (e instanceof CommunicationErrorException) {
				apiServiceVO.setAppStatusCode(Constants.APP_CODE_COMMUNICATION_ERROR);
				message.append("CommunicationErrorException : ");
			} else if (e instanceof ErrorException) {
				apiServiceVO.setAppStatusCode(((ErrorException) e).getErrorCode());
				message.append("Exception : ");
			} else {
				apiServiceVO.setAppStatusCode(Constants.APP_CODE_APPLICATION_ERROR);
				message.append("Exception : ");
			}
			message.append(Constants.NEXT_LINE).append(helper.toJSON(apiServiceVO));
			logger.error(message, e);
		}
	}

	protected Object getValidatedBody(HttpEntity<?> httpEntity) throws InputPayloadException {
		final Object body = httpEntity.getBody();
		validateInputPayload(body);
		return body;
	}

	private void validateInputPayload(Object vo) throws InputPayloadException {
		ArrayList<String> messages = new ArrayList<>();

		if (vo == null) {
			throw new InputPayloadException(messages);
		}

		DirectFieldBindingResult result = new DirectFieldBindingResult(vo, vo.getClass().getName());
		validator.validate(vo, result);
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			Iterator<ObjectError> iterator = errors.iterator();
			ObjectError obj;
			while (iterator.hasNext()) {
				obj = iterator.next();
				messages.add(obj.getDefaultMessage());
			}
			throw new InputPayloadException(messages);
		}
	}

	protected String getIpAddress(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_IP_ADDRESS));
	}

	protected String getDeviceId(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_DEVICE_ID));
	}

	protected String getAuthToken(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_AUTH_TOKEN));
	}

	protected String getLoginId(HttpHeaders header)  {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_RESTAURANT_ID));
	}

	protected String getLatitude(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_LATITUDE));
	}

	protected String getLongitude(HttpHeaders header) {
		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_LONGITUDE));
	}

	protected Long authenticateRequest(HttpHeaders header) throws UserAuthenticationException, AppErrorException {
		if (header == null) { throw new AppErrorException(null, "Invalid null header"); }
		Long loginId;

		final String id = getLoginId(header);
		try {
			loginId = dataParser.getLongValue(id);
		} catch (Exception e) {
			throw new AppErrorException(id, "INVALID RESTAURANT ID");
		}

		if (isValidationON()) {
			RestaurantsSessionsVO session = restaurantsSessionsServices.findUserSessionByLoginIdAndDeviceId(loginId, getDeviceId(header));
			if (session == null) {
				throw new UserAuthenticationException(Constants.APP_CODE_LOGOUT, loginId + "-" + header.getFirst(Constants.HEADER_DEVICE_ID), "SESSION NOT FOUND");

			} else if (getDeviceId(header) == null || getAuthToken(header) == null) {
				throw new UserAuthenticationException(loginId + "->" + header.toString(), "INVALID HEADER");

			} else if (!session.getSessionToken().equals(getAuthToken(header))) {
				restaurantsSessionsServices.logout(loginId, getDeviceId(header));
				throw new UserAuthenticationException(Constants.APP_CODE_LOGOUT, loginId + "->" + session.getSessionToken() + " does not match with " + getAuthToken(header) + helper.toJSON(header), "SESSION DOES NOT MATCH");

			} else if (this.isDateExpired(session.getLogoutOrInvalidationDatetime()) && session.getStatus().equals(Constants.STATUS_ACTIVE)) {
				restaurantsSessionsServices.staleActiveSessions(loginId, session.getDeviceId());
				throw new UserAuthenticationException(Constants.APP_CODE_LOGOUT, loginId + "->" + header.toString(), "INVALID SESSION");
			}
			else if (!session.getStatus().equals(Constants.STATUS_ACTIVE)) {
				throw new UserAuthenticationException(Constants.APP_CODE_LOGOUT, loginId + "->" + header.toString(), "INVALID SESSION");
			}
		}
		return loginId;
	}


//	 non authenticated response
	protected ResponseEntity<ResponseVO> getBareResponseEntity(Logger logger, APIServiceVO vo) {
		ResponseEntity<ResponseVO> responseEntity = getBareResponseEntityWithoutLog(vo);
		logFormattedPayload(logger, vo, responseEntity);
		return responseEntity;
	}

	private void logFormattedPayload(Logger logger, APIServiceVO vo, ResponseEntity<ResponseVO> responseEntity) {
		if (logger.isDebugEnabled()) {
			if (isGsonON()) {
				Object obj = helper.fromJSON(vo.getPayload(), vo.getPayloadClass());
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				logger.debug(gson.toJson(obj));
			} else {
				logger.debug(helper.toJSON(responseEntity));
			}
		}
	}

	protected ResponseEntity<ResponseVO> getBareResponseEntity(APIServiceVO vo) {
		return getBareResponseEntityWithoutLog(vo);
	}

	protected ResponseEntity<ResponseVO> getBareResponseEntityWithoutLog(APIServiceVO vo) {
		ResponseVO response = new ResponseVO();
		response.setAppStatusCode(vo.getAppStatusCode());
		response.setPayload(vo.getPayload());
		return new ResponseEntity<>(response, new HttpHeaders(), getHttpStatus());
	}

	protected ResponseEntity<ResponseVO> getAuthenticatedResponseEntity(Logger logger, APIServiceVO vo, HttpHeaders hh) {
		ResponseEntity<ResponseVO> responseEntity = getAuthenticatedResponseEntityWithoutLog(logger, vo, hh);
		logFormattedPayload(logger, vo, responseEntity);
		return responseEntity;
	}

	protected ResponseEntity<ResponseVO> getAuthenticatedResponseEntityWithoutLog(Logger logger, APIServiceVO vo, HttpHeaders hh) {
		String authToken = hh.getFirst(Constants.HEADER_AUTH_TOKEN);
		Long loginId = null;
		try {
			loginId = dataParser.validateId(hh.getFirst(Constants.HEADER_RESTAURANT_ID));
			if (isValidationON()) {
				String deviceId = getDeviceId(hh);

				// authToken = getUpdatedSessionToken(loginId, deviceId);
				RestaurantsSessionsVO us = restaurantsSessionsServices.findUserSessionByLoginIdAndDeviceId(loginId, deviceId);
				if (us == null) { throw new RecordNotFoundException("SESSION NOT FOUND"); }
				authToken = us.getSessionToken();
			}
		} catch (Exception e) {
			logger.error("Authenticate Response Error :" + e.getMessage() + Constants.NEXT_LINE + helper.toJSON(hh));
			vo.setAppStatusCode(Constants.APP_CODE_LOGOUT);
		}

		ResponseVO response = new ResponseVO();
		response.setAppStatusCode(vo.getAppStatusCode());
		response.setMessages(vo.getMessages());
		response.setPayload(vo.getPayload());

		HttpHeaders header = new HttpHeaders();
		header.set(Constants.HEADER_AUTH_TOKEN, authToken);
		header.set(Constants.HEADER_DEVICE_ID, hh.getFirst(Constants.HEADER_DEVICE_ID));

		return new ResponseEntity<>(response, header, getHttpStatus());
	}

//	 used for create and login user ONLY
	protected ResponseEntity<ResponseVO> getLoginResponseEntity(Logger logger, APIServiceVO vo, HttpHeaders hh, Long loginId) {

		String authToken = Constants.EMPTY_STRING;
		String deviceId = helper.isEmpty(getDeviceId(hh)) ? Calendar.getInstance().getTimeInMillis() + Constants.EMPTY_STRING : helper.getTrimmedUpperCase(getDeviceId(hh));

		String ipAddress = helper.isEmpty(getIpAddress(hh)) ? "0.0.0.0" : helper.getTrimmedUpperCase(getIpAddress(hh));

		String latitude = helper.isEmpty(getLatitude(hh)) ? "0.0" : helper.getTrimmedUpperCase(getLatitude(hh));

		String longitude = helper.isEmpty(getLongitude(hh)) ? "0.0" : helper.getTrimmedUpperCase(getLongitude(hh));


		try {
			if (isValidationON()) {
				authToken = getActiveUserSessionToken(loginId, deviceId);
				if (authToken == null) { authToken = getNewSessionToken(loginId, deviceId); }
			}
		} catch (Exception e) {
			logger.error("Login Response Error :" + e.getMessage() + Constants.NEXT_LINE + helper.toJSON(hh));
			vo.setAppStatusCode(Constants.APP_CODE_LOGOUT);
			ArrayList<String> messages = new ArrayList<>();
			vo.setMessages(messages);
		}

		ResponseVO response = new ResponseVO();
		response.setAppStatusCode(vo.getAppStatusCode());
		response.setMessages(vo.getMessages());
		response.setPayload(vo.getPayload());

		HttpHeaders header = new HttpHeaders();
		header.set(Constants.HEADER_AUTH_TOKEN, authToken);
		header.set(Constants.HEADER_DEVICE_ID, deviceId);
		header.set(Constants.HEADER_RESTAURANT_ID, String.valueOf(loginId));

		addToLoginHistory(loginId, deviceId, ipAddress,latitude + ":" + longitude);

		ResponseEntity<ResponseVO> responseEntity = new ResponseEntity<>(response, header, getHttpStatus());
		if (logger.isDebugEnabled()) {
			logger.debug(helper.toJSON(responseEntity));
		}
		return responseEntity;
	}

	private void addToLoginHistory(Long loginId, String deviceId, String ipAddress, String latitudeLongitude) {
		loginHistoryServices.save(getLoginHistoryVO(loginId, deviceId, ipAddress, latitudeLongitude));
	}

	private boolean isValidationON() {
		return helper.getSystemProperty("authToken", "false");
	}

	private boolean isGsonON() {
		return !helper.getSystemProperty("gson", "true");
	}

	private boolean isDateExpired(LocalDateTime logoutOrInvalidationDatetime) {
		boolean ret = false;
		if (logoutOrInvalidationDatetime != null && helper.getCurrentTime().compareTo(logoutOrInvalidationDatetime) >= 0) {
			ret = true;
		}
		return ret;
	}

	private String getActiveUserSessionToken(Long loginId, String deviceId) {
		String authToken = null;
		RestaurantsSessionsVO restaurantsSessionsVO = restaurantsSessionsServices.findUserSessionByLoginIdAndDeviceId(loginId, deviceId);
		if (restaurantsSessionsVO != null && restaurantsSessionsVO.getStatus().equalsIgnoreCase(Constants.STATUS_ACTIVE)) {
			authToken = restaurantsSessionsVO.getSessionToken();
		}
		return authToken;
	}

	private String getNewSessionToken(Long loginId, String deviceId) throws AppErrorException, DataFormatException {
		String sys = "7884000000";
		LocalDateTime currentTimestamp = helper.getCurrentTime();
		LocalDateTime expiryTime = currentTimestamp.plusSeconds(dataParser.getLongValue(sys)/1000);
		String authToken = getAuthToken(expiryTime);
		restaurantsSessionsServices.createNewUserSession(loginId, deviceId, authToken, expiryTime);
		return authToken;
	}

	private String getUpdatedSessionToken(long loginId, String deviceId) throws Exception {
		String authToken = getAuthToken(helper.getCurrentTime());
		return authToken;
	}

	private String getAuthToken(LocalDateTime expiryTime) throws AppErrorException {
		String key = (UUID.randomUUID().toString() + (Constants.EMPTY_STRING + expiryTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())).replaceAll("-", Constants.EMPTY_STRING);
		String authToken = "Hello";
		return authToken;
	}

	private HttpStatus getHttpStatus() {
		return HttpStatus.OK;
	}

	private LoginHistoriesVO getLoginHistoryVO(Long loginId, String deviceId, String ipAddress, String latitudeLongitude) {
		LoginHistoriesVO vo = new LoginHistoriesVO();
		LocalDateTime currentTime = helper.getCurrentTime();
		vo.setDeviceId(deviceId);
		vo.setLoginDatetime(currentTime);
		vo.setLoginId(loginId);
		vo.setIpAddress(ipAddress);
		vo.setLatitudeLongitude(latitudeLongitude);
		vo.setLoginStatus(Constants.LOGIN_SUCCESS);
		return vo;
	}
}
