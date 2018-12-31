package com.restaurants.restaurants.exceptions;

import com.restaurants.restaurants.utils.Constants;

public class DuplicateRecordException extends WarningException {

	private static final long serialVersionUID = 4268735337842867023L;

	public DuplicateRecordException(String message) {
		super(message);
	}

}
