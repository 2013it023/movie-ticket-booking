package com.superops.ai.movieticketbooking.core.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.superops.ai.movieticketbooking.core.pojo.BannerMessage;
import com.superops.ai.movieticketbooking.core.pojo.ErrorInfo;
import com.superops.ai.movieticketbooking.core.pojo.TicketBookingResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionHandlers class handles the exception caught under com.superops.ai
 * packages.
 * 
 * @author Saravanan Perumal
 *
 */
@RestControllerAdvice(basePackages = "com.superops.ai")
@Slf4j
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

	/**
	 * handleServiceException will be executed when
	 * MovieTicketBookingException.class throw at service level
	 * 
	 * @param servletRequest
	 * @param exception
	 * @param webRequest
	 * @return error response
	 */
	@ExceptionHandler(MovieTicketBookingException.class)
	public TicketBookingResponse<Object> handleServiceException(HttpServletRequest servletRequest,
			MovieTicketBookingException exception, WebRequest webRequest) {

		TicketBookingResponse<Object> response = buildServiceResponse(servletRequest, exception);

		return response;
	}

	private TicketBookingResponse<Object> buildServiceResponse(HttpServletRequest servletRequest,
			MovieTicketBookingException exception) {

		log.error(exception.getErrorMessage());

		TicketBookingResponse<Object> response = new TicketBookingResponse<>();

		ErrorInfo errorInfo = ErrorInfo.builder().e2eRequestId(servletRequest.getHeader("e2eRequestId"))
				.errorCode(exception.getErrorCode()).errorMessage(exception.getErrorMessage()).errorName("ERROR")
				.timeStamp(LocalDateTime.now().format(formatter)).build();

		response.setErrorInfo(errorInfo);
		response.setBannerMessage(BannerMessage.builder().errorCode(exception.getErrorCode())
				.errorMessage(exception.getErrorMessage()).errorType("ERROR").build());

		return response;
	}

}
