package com.restaurants.utils;

import javax.ws.rs.core.MediaType;

public class Constants {

    private Constants() { super (); }

	public static final String ACCEPT_TYPE_JSON = "Accept=application/json" ;
	public static final String APPLICATION_XML_VALUE = "application/xml";
	public static final String APPLICATION_PDF_VALUE = "application/pdf";
	public static final String APPLICATION_TEXT_VALUE = "application/text";
	public static final String APPLICATION_JSON_VALUE = "application/json";
	public static final String APPLICATION_IMAGE_JPG = "image/jpeg";
	public static final String APPLICATION_MULTIPART_FORM_DATA = MediaType.MULTIPART_FORM_DATA;
	public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

	public static final int APP_CODE_MISSING_FIELDS = 1;
	public static final int APP_CODE_DUPLICATE_RECORD = 2;
	public static final int APP_CODE_INVALID_DATA_FORMAT = 3;
	public static final int APP_CODE_DATA_INPUT_ERROR = 4;
	public static final int APP_CODE_RECORD_NOT_FOUND = 5;
	public static final int APP_CODE_LOGIN_FAILED = 6;
	public static final int APP_CODE_INACTIVE = 7;
	public static final int APP_CODE_PENDING = 8;
	public static final int APP_CODE_LOCKED = 9;
	public static final int APP_CODE_LOGOUT = 10;
	public static final int APP_CODE_INVALID_REQUEST = 11;
	public static final int APP_CODE_REQUEST_ALREADY_PROCCESS = 12;
	public static final int APP_CODE_COMMUNICATION_ERROR = 13;
	public static final int APP_CODE_DATA_CALCULATION_ERROR = 14;
	public static final int APP_CODE_UNAUTHORIZED_ERROR = 15;
	public static final int APP_CODE_AUTHENTICATION_ERROR = 16;
	public static final int APP_CODE_NEW_TERMS_PRIVACIES = 17;
	public static final int APP_CODE_APPLICATION_ERROR = 100;

	public static final int APP_CODE_SUCCESS = 0;

	public static final String FORWARD_SLASH = "/";
	public static final String PIPE = "|";
	public static final String SINGLE_SPACE = " ";
	public static final String SPECIAL_CHARACTERS = "!@#$%^&*";
	public static final String COMMA = ",";
	public static final String NEXT_LINE = "\n";
	public static final String HTML_LINEBREAK = "<br/>";
	public static final String URL_SPACE = "%20";
	public static final String PERCENTAGE = "%";
	public static final String ESCAPE_PERCENTAGE ="\\%";
	public static final String SPACE_DELIMITER = " ";
	public static final String ENCODING_FORMAT = "UTF-8";
	public static final String HYPHEN = "-";
	public static final String EMPTY_STRING = "";


	public static final String TIMESTAMP_NULL = "0000-00-00 00:00:00";
	public static final String SQL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String SQL_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String HEADER_RESTAURANT_ID = "X-com-restaurant-id";
	public static final String HEADER_AUTH_TOKEN = "X-com-auth-token";
	public static final String HEADER_DEVICE_ID = "X-com-device-id";
	public static final String HEADER_IP_ADDRESS = "X-com-ip-address";
	public static final String HEADER_LATITUDE = "X-com-latitude";
	public static final String HEADER_LONGITUDE = "X-com-longitude";

	public static final String NOT_REGISTERED = "NOT_REGISTERED";
	public static final String CREDENTIALS_DO_NOT_MATCH = "Your credential do not match";
	public static final long INVALID_ID = -1L ;
	public static final long ROOT_USER = 1L;

	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_STALE = "X";
	public static final String STATUS_LOGOUT = "L";

	public static final String LOGIN_SUCCESS = "S";

}
