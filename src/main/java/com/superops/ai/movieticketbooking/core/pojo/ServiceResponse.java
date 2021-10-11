package com.superops.ai.movieticketbooking.core.pojo;

import lombok.Data;

@Data
public class ServiceResponse<T> {

	private T data;

	private ErrorInfo errorInfo;

}
