package com.superops.ai.movieticketbooking.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerMessage {

	public String errorCode;

	public String errorMessage;

	public String errorType;

}
