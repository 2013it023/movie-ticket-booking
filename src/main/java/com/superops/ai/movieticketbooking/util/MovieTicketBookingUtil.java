package com.superops.ai.movieticketbooking.util;

import java.util.List;
import java.util.stream.Collectors;

import com.superops.ai.movieticketbooking.constant.MovieTicketBookingConstant;
import com.superops.ai.movieticketbooking.core.pojo.TokenDto;
import com.superops.ai.movieticketbooking.entity.BlockSeatsEntity;
import com.superops.ai.movieticketbooking.pojo.UserSelectedSeatRequest;

public class MovieTicketBookingUtil {

	public static List<BlockSeatsEntity> createBlockSeatsEntityRequest(UserSelectedSeatRequest blockSeats,
			TokenDto tokenDto) {

		List<BlockSeatsEntity> blockSeatsEntityList = blockSeats.getSeatsToReserveList().stream().map(seatDetail -> {

			BlockSeatsEntity blockSeatsEntity = new BlockSeatsEntity();

			StringBuilder primaryKey = new StringBuilder();
			primaryKey.append(blockSeats.getTheatreId());
			primaryKey.append(MovieTicketBookingConstant.PRIMARY_KEY_JOINER);
			primaryKey.append(blockSeats.getMovieId());
			primaryKey.append(MovieTicketBookingConstant.PRIMARY_KEY_JOINER);
			primaryKey.append(blockSeats.getCinemaHallId());
			primaryKey.append(MovieTicketBookingConstant.PRIMARY_KEY_JOINER);
			primaryKey.append(blockSeats.getShowTimingId());
			primaryKey.append(MovieTicketBookingConstant.PRIMARY_KEY_JOINER);
			primaryKey.append(seatDetail);

			blockSeatsEntity.setId(primaryKey.toString());
			blockSeatsEntity.setUserId(tokenDto.getUserId());
			blockSeatsEntity.setState(MovieTicketBookingConstant.SEAT_STATE_BLOCKED);
			blockSeatsEntity.setTimeStamp(blockSeats.getTimeStamp());

			return blockSeatsEntity;

		}).collect(Collectors.toList());

		return blockSeatsEntityList;
	}

	public static List<String> createPrimaryKeyByReservarationIdAndSeat(String reservarationId,
			List<String> selectedSeatList) {
		return selectedSeatList.stream().map(seat -> {
			return reservarationId + MovieTicketBookingConstant.PRIMARY_KEY_JOINER + seat;
		}).collect(Collectors.toList());
	}

}
