package com.superops.ai.movieticketbooking.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

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

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

		String sso_jwt = httpServletRequest.getHeader("sso_jwt");
		String apiKey = httpServletRequest.getHeader("x-api-key");
		String amId = httpServletRequest.getHeader("am_id");

		if (StringUtils.isNotEmpty(sso_jwt) || StringUtils.isNotEmpty(apiKey)) {

			// API KEY VALIDATION
			if (!apiKey.equals(environment.getProperty("x-api-key"))) {
				throw new RuntimeException("Unauthorized Access", new Throwable("Invalid Identifiers"));
			}

			// SSO_JWT TOKEN VALIDATION

			// Assume we are getting the SSO_JWT token from headers and decrypting it by
			// using the public key and finally we are getting the Claims as a response

		} else {
			throw new RuntimeException("Unauthorized Access");
		}

		chain.doFilter(httpServletRequest, servletResponse);

	}

}
