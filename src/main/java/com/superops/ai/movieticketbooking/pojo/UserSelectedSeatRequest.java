package com.superops.ai.movieticketbooking.pojo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSelectedSeatRequest {

	@NotNull
	public String theatreId;

	@NotNull
	public String cinemaHallId;

	@NotNull
	public String movieId;

	@NotNull
	public String showTimingId;

	@Size(min = 1, max = 6)
	public List<String> seatsToReserveList;

	public String timeStamp;

}
