package com.superops.ai.movieticketbooking.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.superops.ai.movieticketbooking.constant.MovieTicketBookingConstant;
import com.superops.ai.movieticketbooking.pojo.BookTicketRequest;
import com.superops.ai.movieticketbooking.pojo.PaymentRequest;
import com.superops.ai.movieticketbooking.pojo.PaymentResponse;

@Component
public class ThirdPartyPaymentHandler {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment environment;

	@Value("${superops.payment.url: }")
	private String url;

	@Value("${superops.mock.enabled: Y}")
	private String mockEnabled;

	public PaymentRequest buildRequest(BookTicketRequest bookTicketRequest) {

		PaymentRequest request = new PaymentRequest();
		request.setPaymentAmount(bookTicketRequest.getPaymentAmount());
		request.setCurrency(MovieTicketBookingConstant.INDIAN_CURRENCY_CODE);
		request.setCardInfo(bookTicketRequest.getCardInfo());

		return request;
	}

	public PaymentResponse execute(PaymentRequest request) {

		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON);

		HttpHeaders headers = new HttpHeaders();
		headers.set(MovieTicketBookingConstant.CLIENT_ID, environment.getProperty("superops.client.id", "SuperOps"));
		headers.set(MovieTicketBookingConstant.AUTHORIZATION_ID,
				environment.getProperty("superops.authorization.id", "SuperOps"));
		headers.setAccept(mediaTypes);

		HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

		if (mockEnabled.equals("Y")) {

			ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(url, entity, PaymentResponse.class);

			if (response != null && response.getStatusCode() == HttpStatus.OK) {
				return response.getBody();
			}
		} else {
			PaymentResponse response = new PaymentResponse();
			response.setStatusCode(MovieTicketBookingConstant.SUCCESS_CODE);
			response.setStatusMessage(MovieTicketBookingConstant.SUCCESS_MESSAGE);
			response.setTransactionId(UUID.randomUUID().toString());

			return response;
		}

		return null;
	}

	public boolean isPaymentSuccess(PaymentResponse response) {

		return response != null && response.getStatusCode() == MovieTicketBookingConstant.SUCCESS_CODE
				&& response.getStatusMessage() == MovieTicketBookingConstant.SUCCESS_MESSAGE;

	}

}
