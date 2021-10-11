package com.superops.ai.movieticketbooking.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillingAddress {

	public String streetName;

	public String district;

	public String state;

	public String pincode;

}
