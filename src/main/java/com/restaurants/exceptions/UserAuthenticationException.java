package com.restaurants.exceptions;


import com.restaurants.utils.Constants;

public class UserAuthenticationException extends ErrorException {

	private static final long serialVersionUID = -4628594656015065875L;
	
	public UserAuthenticationException(String value, String message) {
		super(Constants.APP_CODE_AUTHENTICATION_ERROR, value, message);
	}

	public UserAuthenticationException(int code, String value, String message) {
		super(code, value, message);
	}


}
