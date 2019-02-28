package com.restaurants.exceptions;


import com.restaurants.utils.Constants;

public class InvalidRequestException extends ErrorException {

	public InvalidRequestException() {
		super(Constants.APP_CODE_INVALID_REQUEST, Constants.EMPTY_STRING, Constants.EMPTY_STRING);
	}

	public InvalidRequestException(String value, String message) {
		super(Constants.APP_CODE_INVALID_REQUEST, value, message);
	}

	public InvalidRequestException(String value, String message, String locale) {
		super(Constants.APP_CODE_INVALID_REQUEST, value, message, locale);
	}
}
