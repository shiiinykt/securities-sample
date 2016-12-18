package com.heroku.shiiinykt_securiteis_sample.line.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.heroku.shiiinykt_securiteis_sample.line.CallbackException;
import com.heroku.shiiinykt_securiteis_sample.line.CallbackService;
import com.linecorp.bot.client.LineSignatureValidator;
import com.linecorp.bot.model.event.CallbackRequest;

import spark.Request;

public class CallbackServiceImpl implements CallbackService {

	@Override
	public CallbackRequest handle(Request req) throws CallbackException, IOException {
		String signature = req.headers(CallbackService.X_LINE_CHANNEL_SIGNATURE);
		return handle(signature, new String(req.bodyAsBytes(), StandardCharsets.UTF_8));
	}

	@Override
	public CallbackRequest handle(String signature, String payload) throws CallbackException, IOException {
		if (StringUtils.isEmpty(signature)) {
			throw new CallbackException("Missing 'X-Line-Signature' header");
		}

		final byte[] json = payload.getBytes(StandardCharsets.UTF_8);
		LineSignatureValidator validator = new LineSignatureValidator(System.getenv("CHANNEL_SECRET").getBytes());

		if (!validator.validateSignature(json, signature)) {
			throw new CallbackException("Invalid API signature");
		}

		final CallbackRequest callbackRequest = buildObjectMapper().readValue(json, CallbackRequest.class);
		if (callbackRequest == null || callbackRequest.getEvents() == null) {
			throw new CallbackException("Invalid content");
		}
		return callbackRequest;
	}

	private static ObjectMapper buildObjectMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		objectMapper.registerModule(new JavaTimeModule())
				.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

		return objectMapper;
	}

}
