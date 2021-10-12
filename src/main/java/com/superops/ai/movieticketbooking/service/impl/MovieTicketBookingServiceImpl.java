package com.superops.ai.movieticketbooking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.superops.ai.movieticketbooking.constant.MovieTicketBookingConstant;
import com.superops.ai.movieticketbooking.core.exception.MovieTicketBookingException;
import com.superops.ai.movieticketbooking.core.pojo.ServiceResponse;
import com.superops.ai.movieticketbooking.core.pojo.TokenDto;
import com.superops.ai.movieticketbooking.dto.BlockSeatsRepository;
import com.superops.ai.movieticketbooking.entity.BlockSeatsEntity;
import com.superops.ai.movieticketbooking.handler.ThirdPartyPaymentHandler;
import com.superops.ai.movieticketbooking.pojo.BookTicketRequest;
import com.superops.ai.movieticketbooking.pojo.BookTicketResponse;
import com.superops.ai.movieticketbooking.pojo.PaymentResponse;
import com.superops.ai.movieticketbooking.pojo.UserSelectSeatResponse;
import com.superops.ai.movieticketbooking.pojo.UserSelectedSeatRequest;
import com.superops.ai.movieticketbooking.service.MovieTicketBookingService;
import com.superops.ai.movieticketbooking.util.MovieTicketBookingUtil;
import com.superops.ai.movieticketbooking.util.MovieTicketBookingValidation;

/**
 * Implementation for movie ticket booking service
 * 
 * @author Saravanan Perumal
 *
 */
@Service
public class MovieTicketBookingServiceImpl implements MovieTicketBookingService {

	@Autowired
	private ThirdPartyPaymentHandler paymentHandler;

	@Autowired
	private BlockSeatsRepository blockSeatsRepository;

	@Autowired
	private MovieTicketBookingValidation validation;

	@Override
	public ServiceResponse<UserSelectSeatResponse> blockUserSelectedSeats(TokenDto tokenDto,
			UserSelectedSeatRequest blockSeats) {

		// Create Row data.
		List<BlockSeatsEntity> saveInfoList = MovieTicketBookingUtil.createBlockSeatsEntityRequest(blockSeats,
				tokenDto);

		// Insert into the repository
		List<BlockSeatsEntity> savedDetails = blockSeatsRepository.saveAll(saveInfoList);

		ServiceResponse<UserSelectSeatResponse> response = new ServiceResponse<UserSelectSeatResponse>();

		return response;
	}

	@Override
	public ServiceResponse<BookTicketResponse> bookUserSelectedSeat(TokenDto tokenDto,
			BookTicketRequest bookTicketRequest) {

		BookTicketResponse bookTicketResponse = new BookTicketResponse();
		ServiceResponse<BookTicketResponse> response = new ServiceResponse<BookTicketResponse>();

		// Create keys list
		List<String> primaryKeyList = MovieTicketBookingUtil.createPrimaryKeyByReservarationIdAndSeat(
				bookTicketRequest.getReservarationId(), bookTicketRequest.getSelectedSeatList());

		// Get All the Data's by Primary Key
		List<BlockSeatsEntity> seatsStatusList = blockSeatsRepository.findAllById(primaryKeyList);

		// Validate Information.
		validation.isValidTransaction(seatsStatusList, bookTicketRequest, tokenDto);

		// Make Payment
		PaymentResponse paymentResponse = paymentHandler.execute(paymentHandler.buildRequest(bookTicketRequest));

		if (paymentHandler.isPaymentSuccess(paymentResponse)) {

			bookTicketResponse.setPaymentAmount(bookTicketRequest.getPaymentAmount());
			bookTicketResponse.setSeatList(bookTicketRequest.getSelectedSeatList());
			bookTicketResponse.setTransactionId(paymentResponse.getTransactionId());

			List<BlockSeatsEntity> updatedSeatList = seatsStatusList.stream().map(seatDetails -> {
				seatDetails.setState(MovieTicketBookingConstant.SEAT_STATE_BOOKED);
				return seatDetails;
			}).collect(Collectors.toList());

			blockSeatsRepository.saveAll(updatedSeatList);

		} else {
			throw new MovieTicketBookingException(paymentResponse.getStatusCode(),
					"The transaction was declined. Please try again with different Payment.");
		}

		response.setData(bookTicketResponse);

		return response;
	}

}
