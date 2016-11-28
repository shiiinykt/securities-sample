package com.heroku.shiiinykt_securiteis_sample.reference;

import java.util.Map;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.AttributeFactory;
import com.heroku.shiiinykt_securiteis_sample.Meta;
import com.heroku.shiiinykt_securiteis_sample.account.Account;
import com.heroku.shiiinykt_securiteis_sample.utils.ViewUtil;

import spark.Request;
import spark.Response;
import spark.Route;

public class ReferenceController {
	
	private static String ORDERS = "orders";
	
	@Inject
	private static OrderReferenceService service;

	public static Route order = (Request req, Response res) -> {
		Map<String, Object> attributes = AttributeFactory.create(req);
		
		if (req.session().attribute(Meta.Parameter.ACCOUNT) != null) {
			String accountId = ((Account) req.session().attribute(Meta.Parameter.ACCOUNT)).getAccountId();
			attributes.put(ORDERS, service.find(accountId));
		}
		
		return ViewUtil.render(attributes, Meta.Template.REFERENCE_ORDER);
	};
}
