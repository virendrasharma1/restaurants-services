package com.restaurants.restaurants.exceptions;


import com.restaurants.restaurants.utils.Constants;

public class UnAuthorizedException extends ErrorException {

	private static final long serialVersionUID = 3644711150115820381L;
	
	public UnAuthorizedException(String message) {
		super(Constants.APP_CODE_UNAUTHORIZED_ERROR, Constants.EMPTY_STRING, message);
	}

	public UnAuthorizedException(String message, String locale) {
		super(Constants.APP_CODE_UNAUTHORIZED_ERROR, Constants.EMPTY_STRING, message, locale);
	}

}
