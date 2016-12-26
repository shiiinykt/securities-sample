package com.heroku.shiiinykt_securiteis_sample.callback;

import java.io.IOException;

import com.google.inject.ImplementedBy;
import com.heroku.shiiinykt_securiteis_sample.callback.impl.CallbackServiceImpl;
import com.linecorp.bot.model.event.CallbackRequest;

import spark.Request;

@ImplementedBy(CallbackServiceImpl.class)
public interface CallbackService {
	
	static String X_LINE_CHANNEL_SIGNATURE = "X-Line-Signature";

	public CallbackRequest handle(Request req) throws CallbackException, IOException;
	
	public CallbackRequest handle(String signature, String payload) throws CallbackException, IOException;
	
}
