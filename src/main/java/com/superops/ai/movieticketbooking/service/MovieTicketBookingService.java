package com.superops.ai.movieticketbooking.service;

import com.superops.ai.movieticketbooking.core.pojo.ServiceResponse;
import com.superops.ai.movieticketbooking.core.pojo.TokenDto;
import com.superops.ai.movieticketbooking.pojo.UserSelectedSeatRequest;
import com.superops.ai.movieticketbooking.pojo.BookTicketRequest;
import com.superops.ai.movieticketbooking.pojo.BookTicketResponse;
import com.superops.ai.movieticketbooking.pojo.UserSelectSeatResponse;

public interface MovieTicketBookingService {

	ServiceResponse<UserSelectSeatResponse> blockUserSelectedSeats(TokenDto tokenDto,
			UserSelectedSeatRequest blockSeats);

	ServiceResponse<BookTicketResponse> bookUserSelectedSeat(TokenDto tokenDto, BookTicketRequest blockSeats);

}
