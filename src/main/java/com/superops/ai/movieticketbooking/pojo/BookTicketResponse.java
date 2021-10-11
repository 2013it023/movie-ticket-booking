package com.superops.ai.movieticketbooking.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTicketResponse {

	private String transactionId;

	private String paymentAmount;

	private List<String>seatList;
}
