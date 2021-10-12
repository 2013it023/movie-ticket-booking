package com.superops.ai.movieticketbooking.core.exception;

import lombok.Getter;

/**
 * 
 * MovieTicketBookingException - Common exception class for entire module.
 * 
 * @author Saravanan Perumal
 *
 */
@Getter
public class MovieTicketBookingException extends RuntimeException {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String errorCode;

	private String errorMessage;

	public MovieTicketBookingException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public MovieTicketBookingException(String errorCode, String errorMessage, Throwable t) {
		super(errorMessage, t);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
