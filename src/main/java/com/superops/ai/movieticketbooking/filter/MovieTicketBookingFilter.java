package com.superops.ai.movieticketbooking.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.superops.ai.movieticketbooking.constant.MovieTicketBookingConstant;
import com.superops.ai.movieticketbooking.core.pojo.TokenDto;

import io.jsonwebtoken.Jwts;

/**
 * 
 * MovieTicketBookingFilter - Security Filter to secure the endpoints.
 * 
 * @author Saravanan Perumal
 *
 */
@Component
public class MovieTicketBookingFilter extends GenericFilterBean {

	@Autowired
	private Environment environment;

	@Autowired
	private ObjectMapper mapper;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

		String sso_jwt = getToken(httpServletRequest);
		String apiKey = httpServletRequest.getHeader("x-api-key");
		String amId = httpServletRequest.getHeader("am_id");

		if (StringUtils.isNotEmpty(sso_jwt) || StringUtils.isNotEmpty(apiKey) || StringUtils.isNotEmpty(amId)) {

			// API KEY VALIDATION
			if (!apiKey.equals(environment.getProperty("x-api-key"))) {
				throw new RuntimeException("Unauthorized Access", new Throwable("Invalid Identifiers"));
			}

			// SSO_JWT TOKEN VALIDATION

			TokenDto decodeToken = decodeToken(sso_jwt);

			if (decodeToken == null || StringUtils.isEmpty(decodeToken.getUserId())) {
				throw new RuntimeException("Unauthorized Access", new Throwable("Invalid Identifiers"));
			}

			// Assume we are getting the SSO_JWT token from headers and decrypting it by
			// using the public key and finally we are getting the Claims as a response

		} else {
			throw new RuntimeException("Unauthorized Access");
		}

		chain.doFilter(httpServletRequest, servletResponse);

	}

	private TokenDto decodeToken(String ssoJwt) {

		String decodedValue = Jwts.parser().setSigningKey(environment.getProperty("superops.ai.signingkey", ""))
				.parseClaimsJws(ssoJwt).getBody().getSubject();
		TokenDto tokenDto = null;

		try {
			tokenDto = mapper.readValue(decodedValue, TokenDto.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return tokenDto;

	}

	private String getToken(HttpServletRequest httpServletRequest) {
		Cookie cookie = WebUtils.getCookie(httpServletRequest, MovieTicketBookingConstant.JWT_TOKEN_COOKIE_NAME);
		return cookie != null ? cookie.getValue() : null;
	}

}
