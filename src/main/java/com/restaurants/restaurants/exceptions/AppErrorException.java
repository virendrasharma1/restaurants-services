package com.restaurants.restaurants.exceptions;


import com.restaurants.restaurants.utils.Constants;

public class AppErrorException extends ErrorException {

	private static final long serialVersionUID = 4853190525147827349L;

	public AppErrorException(String value, String message) {
		super(Constants.APP_CODE_APPLICATION_ERROR, value, message);
	}

	public AppErrorException(String value, String message, String locale) {
		super(Constants.APP_CODE_APPLICATION_ERROR, value, message, locale);
	}

	public AppErrorException(long value, String message) {
		super((int) value, Constants.EMPTY_STRING, message);
	}

	public AppErrorException(long value, String message, String locale) {
		super((int) value, Constants.EMPTY_STRING, message, locale);
	}
}
