package com.superops.ai.movieticketbooking.pojo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookTicketRequest {

	@Size(min = 1, max = 6)
	public List<String> selectedSeatList;
	
	@NotNull
	public String reservarationId;

	@NotNull
	public String paymentAmount;

	@NotNull
	public CardInfo cardInfo;

	@NotNull
	public BillingAddress billingAddress;

}
