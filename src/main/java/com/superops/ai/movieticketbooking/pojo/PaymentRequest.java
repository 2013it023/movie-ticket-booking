package com.superops.ai.movieticketbooking.pojo;

import lombok.Data;

@Data
public class PaymentRequest {

	private String paymentAmount;

	private String currency;

	private CardInfo cardInfo;

}
