package com.restaurants.exceptions;

public class RecordNotFoundException extends WarningException {

	private static final long serialVersionUID = -2685024653322348532L;

	public RecordNotFoundException(String message) {
		super(message);
	}
}
