package com.superops.ai.movieticketbooking.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketBookingResponse<T> {

	private T data;

	private ErrorInfo errorInfo;

	private BannerMessage bannerMessage;

}
