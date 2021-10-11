package com.superops.ai.movieticketbooking.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.BiFunction;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.superops.ai.movieticketbooking.core.pojo.ErrorInfo;
import com.superops.ai.movieticketbooking.core.pojo.ServiceResponse;
import com.superops.ai.movieticketbooking.core.pojo.TicketBookingResponse;
import com.superops.ai.movieticketbooking.core.pojo.TokenDto;

public abstract class IRestController {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

	protected <R, T> ResponseEntity<TicketBookingResponse<T>> invokeServiceMethod(HttpServletRequest servletRequest,
			R clientRequest, BiFunction<TokenDto, R, ServiceResponse<T>> serviceMethod) {

		TokenDto tokenDto = createRequestContext(servletRequest);
		ServiceResponse<T> response = serviceMethod.apply(tokenDto, clientRequest);
		return CreateResponse(servletRequest, response);
	}

	private <T> ResponseEntity<TicketBookingResponse<T>> CreateResponse(HttpServletRequest servletRequest,
			ServiceResponse<T> response) {

		TicketBookingResponse<T> ticketBookingResponse = new TicketBookingResponse<>();

		ticketBookingResponse.setData(response.getData());

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode("00");
		errorInfo.setErrorMessage("SUCCESS");
		errorInfo.setErrorName(HttpStatus.OK.name());
		errorInfo.setTimeStamp(LocalDateTime.now().format(formatter));
		errorInfo.setE2eRequestId(UUID.randomUUID().toString());

		ticketBookingResponse.setErrorInfo(errorInfo);

		return new ResponseEntity<>(ticketBookingResponse, HttpStatus.OK);

	}

	private TokenDto createRequestContext(HttpServletRequest servletRequest) {

		// Here create a tokenDto with Customer details based upon the
		// sessionInformation.

		String userId = servletRequest.getHeader("userId");

		TokenDto tokenDto = new TokenDto();
		tokenDto.setUserId(userId);

		return tokenDto;
	}

}
