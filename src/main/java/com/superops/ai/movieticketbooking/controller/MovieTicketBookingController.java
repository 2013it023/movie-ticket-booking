package com.superops.ai.movieticketbooking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.superops.ai.movieticketbooking.core.pojo.TicketBookingResponse;
import com.superops.ai.movieticketbooking.pojo.BookTicketRequest;
import com.superops.ai.movieticketbooking.pojo.BookTicketResponse;
import com.superops.ai.movieticketbooking.pojo.UserSelectSeatResponse;
import com.superops.ai.movieticketbooking.pojo.UserSelectedSeatRequest;
import com.superops.ai.movieticketbooking.service.MovieTicketBookingService;

@RestController
@RequestMapping("/ticket_booking/movies")
public class MovieTicketBookingController extends IRestController {

	@Autowired
	private MovieTicketBookingService ticketBookingService;

	@RequestMapping(path = "/block_seats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketBookingResponse<UserSelectSeatResponse>> blockUserSelectedSeats(
			HttpServletRequest servletRequest, @RequestBody(required = true) @Valid UserSelectedSeatRequest request) {
		return invokeServiceMethod(servletRequest, request, ticketBookingService::blockUserSelectedSeats);
	}

	@RequestMapping(path = "/payment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketBookingResponse<BookTicketResponse>> bookUserSelectedSeat(
			HttpServletRequest servletRequest, @RequestBody(required = true) @Valid BookTicketRequest request) {
		return invokeServiceMethod(servletRequest, request, ticketBookingService::bookUserSelectedSeat);
	}
}
