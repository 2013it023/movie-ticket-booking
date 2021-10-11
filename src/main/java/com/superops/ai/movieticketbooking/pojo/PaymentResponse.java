package com.superops.ai.movieticketbooking.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

	private String transactionId;

	private String createdAt;

	private String statusCode;
	
	private String statusMessage;
}
