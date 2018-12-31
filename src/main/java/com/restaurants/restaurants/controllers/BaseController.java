package com.restaurants.restaurants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class BaseController {
	@Autowired protected Validator validator;
//	@Autowired protected SystemHelper helper;


//	protected APIServiceVO initializeResponseWithoutLog(String message) {
//		return new APIServiceVO(message);
//	}
//
//	protected APIServiceVO initializeResponse(Logger logger, HttpEntity<?> httpEntity, String message) {
//		logger.debug(message);
//		if (logger.isDebugEnabled()) {
//			logger.debug(helper.toJSON(httpEntity));
//		}
//		return new APIServiceVO(message);
//	}
//
//	private String getCallType(String message) {
//		int endIndex = message.indexOf('/', 0);
//		message = message.substring(0 , endIndex);
//		return message.trim();
//	}
//
//	private String getApiUrl(String message) {
//		int beginIndex = message.indexOf('/', 0);
//		message = message.substring(beginIndex);
//		return message.trim();
//	}
//
//	protected void logAPIEntry(Logger logger, HttpEntity<?> httpEntity, String message) {
//		logger.debug(message);
//		if (logger.isDebugEnabled()) {
//			logger.debug(helper.toJSON(httpEntity));
//		}
//	}
//
//	protected String getMessage(String message, String tag, String value) {
//		return message.replace(tag, value);
//	}
//
//	protected void handleAppExceptions(Logger logger, Exception e, APIServiceVO apiServiceVO, HttpEntity<?> httpEntity) {
//		StringBuilder message = new StringBuilder(apiServiceVO.getApiCall() + " Exception Handling :" + Constants.NEXT_LINE);
//		if (httpEntity != null) { message.append(helper.toJSON(httpEntity)).append(Constants.NEXT_LINE); }
//
//		if (e instanceof RecordNotFoundException) {
//			RecordNotFoundException a = (RecordNotFoundException) e;
//			apiServiceVO.setAppStatusCode(Constants.APP_CODE_RECORD_NOT_FOUND);
//			logger.debug(message + "RecordNotFoundException : " + a.getMessage());
//		} else if (e instanceof DuplicateRecordException) {
//			DuplicateRecordException a = (DuplicateRecordException) e;
//			apiServiceVO.setAppStatusCode(Constants.APP_CODE_DUPLICATE_RECORD);
//			logger.debug(message + "DuplicateRecordException : " + a.getMessage());
//		} else if (e instanceof SimpleException) {
//			SimpleException a = (SimpleException) e;
//			apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
//			logger.debug(message + "SimpleException : " + a.getMessage());
//		} else if (e instanceof UnAuthorizedException) {
//			UnAuthorizedException u = (UnAuthorizedException) e;
//			apiServiceVO.setAppStatusCode(Constants.APP_CODE_INVALID_REQUEST);
//			logger.debug("UnAuthorizedException : " + u.getMessage());
//		} else if (e instanceof UserAuthenticationException) {
//			UserAuthenticationException u = (UserAuthenticationException) e;
//			apiServiceVO.setAppStatusCode(u.getErrorCode());
//			logger.debug("UserAuthenticationException : " + u.getMessage());
//		} else {
//
//			if (e instanceof AppErrorException) {
//				AppErrorException a = (AppErrorException) e;
//				apiServiceVO.setAppStatusCode(a.getErrorCode());
//				message.append("AppErrorException : ").append(a.getMessage());
//			} else if (e instanceof DataFormatException) {
//				DataFormatException d = (DataFormatException) e;
//				apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
//				message.append("DataFormatException : ").append(d.getMessage());
//			}  else if (e instanceof InvalidDateException) {
//				apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
//				message.append("InvalidDateException : ");
//			} else if (e instanceof DataIntegrityViolationException) {
//				apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
//				message.append("DataIntegrityViolationException : ");
//			} else if (e instanceof InputPayloadException) {
//				InputPayloadException i = (InputPayloadException) e;
//				apiServiceVO.setAppStatusCode(Constants.APP_CODE_DATA_INPUT_ERROR);
//				message.append("InputPayloadException : ");
//			} else if (e instanceof CommunicationErrorException) {
//				apiServiceVO.setAppStatusCode(Constants.APP_CODE_COMMUNICATION_ERROR);
//				message.append("CommunicationErrorException : ");
//			} else if (e instanceof ErrorException) {
//				apiServiceVO.setAppStatusCode(((ErrorException) e).getErrorCode());
//				message.append("Exception : ");
//			} else {
//				apiServiceVO.setAppStatusCode(Constants.APP_CODE_APPLICATION_ERROR);
//				message.append("Exception : ");
//			}
//			message.append(Constants.NEXT_LINE).append(helper.toJSON(apiServiceVO));
//			logger.error(message, e);
//		}
//	}
//
////	protected String getClassification(HttpHeaders header) {
////		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_CLASSIFICATION));
////	}
////
////	protected String getAppId(HttpHeaders header) {
////		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_APP_ID));
////	}
////
////	protected String getIpAddress(HttpHeaders header) {
////		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_IP_ADDRESS));
////	}
////
////	protected String getDeviceId(HttpHeaders header) {
////		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_DEVICE_ID));
////	}
////
////	protected String getAuthToken(HttpHeaders header) {
////		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_AUTH_TOKEN));
////	}
////
////	protected String getLoginId(HttpHeaders header)  {
////		return dataParser.getTrimmedStringValue(header.getFirst(Constants.HEADER_LOGIN_ID));
////	}
//
////	protected Long authenticateRequest(HttpHeaders header) throws UserAuthenticationException, AppErrorException {
////		if (header == null) { throw new AppErrorException(null, "Invalid null header"); }
////		Long loginId;
////
////		final String id = getLoginId(header);
////		try {
////			loginId = dataParser.getLongValue(id);
////		} catch (Exception e) {
////			throw new AppErrorException(id, Messages.INVALID_LOGIN_ID);
////		}
////
////		if (isValidationON()) {
////			AspirantSessionsVO session = aspirantSessionsServices.findUserSessionByLoginIdAndDeviceId(loginId, getDeviceId(header));
////			if (session == null) {
////				throw new UserAuthenticationException(InvictusStatus.APP_CODE_LOGOUT, loginId + "-" + header.getFirst(Constants.HEADER_DEVICE_ID), Messages.SESSION_NOT_FOUND);
////
////			} else if (getDeviceId(header) == null || getAuthToken(header) == null) {
////				throw new UserAuthenticationException(loginId + "->" + header.toString(), Messages.HEADER_INVALID);
////
////			} else if (!session.getSessionToken().equals(getAuthToken(header))) {
////				aspirantSessionsServices.logout(loginId, getDeviceId(header));
////				throw new UserAuthenticationException(InvictusStatus.APP_CODE_LOGOUT, loginId + "->" + session.getSessionToken() + " does not match with " + getAuthToken(header) + helper.toJSON(header), Messages.SESSION_NOT_MATCH);
////
////			} else if (this.isDateExpired(session.getLogoutOrInvalidationDatetime()) && session.getStatus().equals(InvictusStatus.STATUS_ACTIVE)) {
////				aspirantSessionsServices.staleActiveSessions(loginId, session.getDeviceId());
////				throw new UserAuthenticationException(InvictusStatus.APP_CODE_LOGOUT, loginId + "->" + header.toString(), Messages.SESSION_INVALID);
////			}
////			else if (!session.getStatus().equals(InvictusStatus.STATUS_ACTIVE)) {
////				throw new UserAuthenticationException(InvictusStatus.APP_CODE_LOGOUT, loginId + "->" + header.toString(), Messages.SESSION_INVALID);
////			}
////		}
////		return loginId;
////	}
//
//
//	// non authenticated response
////	protected ResponseEntity<ResponseVO> getBareResponseEntity(Logger logger, APIServiceVO vo) {
////		ResponseEntity<ResponseVO> responseEntity = getBareResponseEntityWithoutLog(vo);
////		logFormattedPayload(logger, vo, responseEntity);
////		return responseEntity;
////	}
//
////	private void logFormattedPayload(Logger logger, APIServiceVO vo, ResponseEntity<ResponseVO> responseEntity) {
////		if (logger.isDebugEnabled()) {
////			if (isGsonON()) {
//////				Object obj = helper.fromJSON(vo.getPayload(), vo.getPayloadClass());
////				Gson gson = new GsonBuilder().setPrettyPrinting().create();
////				logger.debug(gson.toJson(obj));
////			} else {
////				logger.debug(helper.toJSON(responseEntity));
////			}
////		}
////	}
//
//	protected ResponseEntity<ResponseVO> getBareResponseEntity(APIServiceVO vo) {
//		return getBareResponseEntityWithoutLog(vo);
//	}
//
//	protected ResponseEntity<ResponseVO> getBareResponseEntityWithoutLog(APIServiceVO vo) {
//		ResponseVO response = new ResponseVO();
//		response.setAppStatusCode(vo.getAppStatusCode());
//		response.setPayload(vo.getPayload());
//		return new ResponseEntity<>(response, new HttpHeaders(), getHttpStatus());
//	}
//
////	protected ResponseEntity<ResponseVO> getAuthenticatedResponseEntity(Logger logger, APIServiceVO vo, HttpHeaders hh) {
////		ResponseEntity<ResponseVO> responseEntity = getAuthenticatedResponseEntityWithoutLog(logger, vo, hh);
////		logFormattedPayload(logger, vo, responseEntity);
////		return responseEntity;
////	}
//
////	protected ResponseEntity<ResponseVO> getAuthenticatedResponseEntityWithoutLog(Logger logger, APIServiceVO vo, HttpHeaders hh) {
////		String authToken = hh.getFirst(Constants.HEADER_AUTH_TOKEN);
////		Long loginId = null;
////		try {
////			loginId = dataParser.validateId(hh.getFirst(Constants.HEADER_LOGIN_ID));
////			if (isValidationON()) {
////				String deviceId = getDeviceId(hh);
////
////				// authToken = getUpdatedSessionToken(loginId, deviceId);
////				AspirantSessionsVO us = aspirantSessionsServices.findUserSessionByLoginIdAndDeviceId(loginId, deviceId);
////				if (us == null) { throw new RecordNotFoundException(Messages.SESSION_NOT_FOUND); }
////				authToken = us.getSessionToken();
////			}
////		} catch (Exception e) {
////			logger.error("Authenticate Response Error :" + e.getMessage() + Constants.NEXT_LINE + helper.toJSON(hh));
////			vo.setAppStatusCode(InvictusStatus.APP_CODE_LOGOUT);
////		}
////
////		ResponseVO response = new ResponseVO();
////		response.setAppStatusCode(vo.getAppStatusCode());
////		response.setMessages(vo.getMessages());
////		response.setPayload(vo.getPayload());
////		response.setUnread(getUnreadNotificationCount(loginId));
////
////		HttpHeaders header = new HttpHeaders();
////		header.set(Constants.HEADER_AUTH_TOKEN, authToken);
////		header.set(Constants.HEADER_DEVICE_ID, hh.getFirst(Constants.HEADER_DEVICE_ID));
////
////		return new ResponseEntity<>(response, header, getHttpStatus());
////	}
//
//	// used for create and login user ONLY
////	protected ResponseEntity<ResponseVO> getLoginResponseEntity(Logger logger, APIServiceVO vo, HttpHeaders hh, Long loginId) {
////
////		String authToken = Constants.EMPTY_STRING;
////		String deviceId = helper.isEmpty(getDeviceId(hh)) ? Calendar.getInstance().getTimeInMillis() + Constants.EMPTY_STRING : helper.getTrimmedUpperCase(getDeviceId(hh));
////
////		String ipAddress = helper.isEmpty(getIpAddress(hh)) ? "0.0.0.0" : helper.getTrimmedUpperCase(getIpAddress(hh));
////
////		String type = helper.isEmpty(getClassification(hh)) ? Constants.HYPHEN : helper.getTrimmedUpperCase(getClassification(hh));
////
////		try {
////			if (isValidationON()) {
////				authToken = getActiveUserSessionToken(loginId, deviceId);
////				if (authToken == null) { authToken = getNewSessionToken(loginId, type, deviceId); }
////			}
////		} catch (Exception e) {
////			logger.error("Login Response Error :" + e.getMessage() + Constants.NEXT_LINE + helper.toJSON(hh));
////			vo.setAppStatusCode(InvictusStatus.APP_CODE_LOGOUT);
////			ArrayList<String> messages = new ArrayList<>();
////			messages.add(getAppMessage(Messages.SESSION_INVALID));
////			vo.setMessages(messages);
////		}
////
////		ResponseVO response = new ResponseVO();
////		response.setAppStatusCode(vo.getAppStatusCode());
////		response.setMessages(vo.getMessages());
////		response.setPayload(vo.getPayload());
////		response.setUnread(getUnreadNotificationCount(loginId));
////
////		HttpHeaders header = new HttpHeaders();
////		header.set(Constants.HEADER_AUTH_TOKEN, authToken);
////		header.set(Constants.HEADER_DEVICE_ID, deviceId);
////		header.set(Constants.HEADER_LOGIN_ID, String.valueOf(loginId));
////
////		addToLoginHistory(loginId, deviceId, ipAddress, latitude + ":" + longitude, type );
////
////		ResponseEntity<ResponseVO> responseEntity = new ResponseEntity<>(response, header, getHttpStatus());
////		if (logger.isDebugEnabled()) {
////			logger.debug(helper.toJSON(responseEntity));
////		}
////		return responseEntity;
////	}
////
////	private void addToLoginHistory(Long loginId, String deviceId, String ipAddress, String latitudeLongitude, String type) {
////		loginHistoryServices.save(getLoginHistoryVO(loginId, deviceId, ipAddress, latitudeLongitude, type));
////	}
//
//	private boolean isValidationON() {
//		return helper.getSystemProperty("authToken", "false");
//	}
//
//	private boolean isGsonON() {
//		return !helper.getSystemProperty("gson", "true");
//	}
//
//	private boolean isDateExpired(LocalDateTime logoutOrInvalidationDatetime) {
//		boolean ret = false;
//		if (logoutOrInvalidationDatetime != null && helper.getCurrentTime().compareTo(logoutOrInvalidationDatetime) >= 0) {
//			ret = true;
//		}
//		return ret;
//	}
//
////	private String getActiveUserSessionToken(Long loginId, String deviceId) {
////		String authToken = null;
////		AspirantSessionsVO aspirantSessionsVO = aspirantSessionsServices.findUserSessionByLoginIdAndDeviceId(loginId, deviceId);
////		if (aspirantSessionsVO != null && aspirantSessionsVO.getStatus().equalsIgnoreCase(InvictusStatus.STATUS_ACTIVE)) {
////			authToken = aspirantSessionsVO.getSessionToken();
////		}
////		return authToken;
////	}
//
////	private String getNewSessionToken(Long loginId, String type, String deviceId) throws AppErrorException, DataFormatException {
////		String sys = this.getSystemParameter(SystemParameters.LOGIN_TIMEOUT);
////		LocalDateTime currentTimestamp = helper.getCurrentTime();
////		LocalDateTime expiryTime = currentTimestamp.plusSeconds(dataParser.getLongValue(sys)/1000);
////		String authToken = getAuthToken(expiryTime);
////
////		aspirantSessionsServices.createNewUserSession(loginId, type, deviceId, authToken, expiryTime);
////		return authToken;
////	}
//
////	private String getUpdatedSessionToken(long loginId, String deviceId) throws Exception {
////		String authToken = getAuthToken(helper.getCurrentTime());
//////		aspirantSessionsServices.updateUserSession(loginId, deviceId, authToken);
////		return authToken;
////	}
//
////	private String getAuthToken(LocalDateTime expiryTime) throws AppErrorException {
////		String key = (UUID.randomUUID().toString() + (Constants.EMPTY_STRING + expiryTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())).replaceAll("-", Constants.EMPTY_STRING);
////		String authToken = new SecurityHashingServiceImpl().generateHash(key);
////		return authToken;
////	}
//
//	private HttpStatus getHttpStatus(/* int appStatusCode */) {
//		// HttpStatus httpStatus = HttpStatus.OK;
//		// switch (appStatusCode) {
//		// case Constants.APP_CODE_APPLICATION_ERROR : { httpStatus =
//		// HttpStatus.INTERNAL_SERVER_ERROR; break;}
//		// case Constants.APP_CODE_INVALID_REQUEST : { httpStatus =
//		// HttpStatus.BAD_REQUEST; break;}
//		// case Constants.APP_CODE_RECORD_NOT_FOUND: { httpStatus =
//		// HttpStatus.NOT_FOUND; break;}
//		// }
//		return HttpStatus.OK;
//	}
//
////	private LoginHistoriesVO getLoginHistoryVO(Long loginId, String deviceId, String ipAddress, String latitudeLongitude, String type) {
////		LoginHistoriesVO vo = new LoginHistoriesVO();
////		LocalDateTime currentTime = helper.getCurrentTime();
////		vo.setDeviceId(deviceId);
////		vo.setLoginDatetime(currentTime);
////		vo.setLoginId(loginId);
////		vo.setIpAddress(ipAddress);
////		vo.setLatitudeLongitude(latitudeLongitude);
////		vo.setLoginStatus(Constants.LOGIN_SUCCESS);
////		vo.setLoginClassification(type);
////		return vo;
////	}
////
////	private int getUnreadNotificationCount(Long loginId) {
////		return notificationsServices.getUnreadNotificationCount(loginId);
////	}
}
