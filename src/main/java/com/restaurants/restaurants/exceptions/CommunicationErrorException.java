package com.restaurants.restaurants.exceptions;

import com.restaurants.restaurants.utils.Constants;

public class CommunicationErrorException extends ErrorException {

	private static final long serialVersionUID = 4853190525147827349L;
	
	public CommunicationErrorException(String value, String message) {
		super(Constants.APP_CODE_COMMUNICATION_ERROR, value, message);
	}

	public CommunicationErrorException(String value, String message, String locale) {
		super(Constants.APP_CODE_COMMUNICATION_ERROR, value, message, locale);
	}
}
