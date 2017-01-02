package com.heroku.shiiinykt_securiteis_sample.account;

import java.util.Map;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.utils.ViewUtil;
import com.heroku.shiiinykt_securities_sample.AttributeFactory;
import com.heroku.shiiinykt_securities_sample.Meta;

import spark.Request;
import spark.Response;
import spark.Route;

public class AccountContoraller {

	@Inject
	private static AccountService service;
	
	public static Route registory = (Request req, Response res) -> {
		Map<String, Object> attributes = AttributeFactory.create(req);
		
		
		return ViewUtil.render(attributes, Meta.Template.ACCOUNT_REGISTORY);
	};
}
