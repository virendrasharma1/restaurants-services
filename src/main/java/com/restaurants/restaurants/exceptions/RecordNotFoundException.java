package com.restaurants.restaurants.exceptions;

import com.restaurants.restaurants.utils.Constants;

public class RecordNotFoundException extends WarningException {

	private static final long serialVersionUID = -2685024653322348532L;

	public RecordNotFoundException(String message) {
		super(message);
	}
}
