package com.superops.ai.movieticketbooking.pojo;

import lombok.Data;

@Data
public class CardInfo {

	public String encryptedCardNumber;

	public String encryptedSecurityCode;

	public int expiryMonth;

	public int expiryYear;

	public String nickName;

}
