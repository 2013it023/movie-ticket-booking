package com.superops.ai.movieticketbooking.constant;

public class MovieTicketBookingConstant {

	public final static String INDIAN_CURRENCY_CODE = "INR";
	public final static String SUCCESS_CODE = "00";
	public final static String SUCCESS_MESSAGE = "SUCCESS";
	public final static String CLIENT_ID = "clientId";
	public final static String AUTHORIZATION_ID = "authorizationId";

	public final static String SEAT_STATE_BLOCKED = "blocked";
	public static final String SEAT_STATE_BOOKED = "booked";
	public final static String PRIMARY_KEY_JOINER = "#";

	// Error Code
	public final static String INVALID_DATA_CODE = "1001";
	public final static String SEAT_UNAVAILABLE_CODE = "1002";

	// Error Message
	public final static String INVALID_DATA_MESSAGE = "We have experienced technical error. Please try again later";
	public final static String SEAT_UNAVAILABLE_MESSAGE = "One of the selected seat was unavailable. Please try again with different seat.";
}
