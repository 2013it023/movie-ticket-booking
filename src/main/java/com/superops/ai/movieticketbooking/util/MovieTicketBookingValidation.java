package com.superops.ai.movieticketbooking.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.superops.ai.movieticketbooking.constant.MovieTicketBookingConstant;
import com.superops.ai.movieticketbooking.core.exception.MovieTicketBookingException;
import com.superops.ai.movieticketbooking.core.pojo.TokenDto;
import com.superops.ai.movieticketbooking.dto.BlockSeatsRepository;
import com.superops.ai.movieticketbooking.entity.BlockSeatsEntity;
import com.superops.ai.movieticketbooking.pojo.BookTicketRequest;

/**
 * MovieTicketBookingValidation contains methods to perform validation and
 * restriction.
 * 
 * @author Saravanan Perumal
 *
 */
@Component
public class MovieTicketBookingValidation {

	@Autowired
	private BlockSeatsRepository repository;

	public void isValidTransaction(List<BlockSeatsEntity> seatsStatusList, BookTicketRequest bookTicketRequest,
			TokenDto tokenDto) {

		// When there is no seat Information found or mismatch
		if (seatsStatusList == null || seatsStatusList.isEmpty()
				|| seatsStatusList.size() != bookTicketRequest.getSelectedSeatList().size()) {
			throw new MovieTicketBookingException(MovieTicketBookingConstant.INVALID_DATA_CODE,
					MovieTicketBookingConstant.INVALID_DATA_MESSAGE);
		}

		// When the seat state, userId mismatch.
		seatsStatusList.stream().forEach(seatStatusEntity -> {

			if (!seatStatusEntity.getState().equals(MovieTicketBookingConstant.SEAT_STATE_BLOCKED)
					|| !seatStatusEntity.getUserId().equals(tokenDto.getUserId())) {
				throw new MovieTicketBookingException(MovieTicketBookingConstant.SEAT_UNAVAILABLE_CODE,
						MovieTicketBookingConstant.SEAT_UNAVAILABLE_MESSAGE);
			}

		});

		// When user took more than 2 minutes to book a ticket.
		Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
		seatsStatusList.stream().forEach(seatStatusEntity -> {

			Timestamp seatTimeStamp = new Timestamp(Long.valueOf(seatStatusEntity.getTimeStamp()));
			long milliseconds = currentTimeStamp.getTime() - seatTimeStamp.getTime();
			int seconds = (int) milliseconds / 1000;

			int minutes = (seconds % 3600) / 60;

			if (minutes > 2) {

				repository.deleteAllById(MovieTicketBookingUtil.createPrimaryKeyByReservarationIdAndSeat(
						bookTicketRequest.getReservarationId(), bookTicketRequest.getSelectedSeatList()));

				throw new MovieTicketBookingException(MovieTicketBookingConstant.SEAT_UNAVAILABLE_CODE,
						MovieTicketBookingConstant.SEAT_UNAVAILABLE_MESSAGE);
			}
		});

	}

}
