package com.restaurants.exceptions;

public class ErrorException extends Exception {

	private static final long serialVersionUID = 7662782479345726671L;
	private int errorCode ;

	public ErrorException(int code, String value, String message) {
		this.errorCode = code;
	}

	public ErrorException(int code, String value, String message, String locale) {
		this.errorCode = code;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
