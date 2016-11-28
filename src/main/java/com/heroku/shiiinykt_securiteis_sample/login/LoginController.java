package com.heroku.shiiinykt_securiteis_sample.login;

import java.util.Map;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.AttributeFactory;
import com.heroku.shiiinykt_securiteis_sample.Meta;
import com.heroku.shiiinykt_securiteis_sample.account.Account;
import com.heroku.shiiinykt_securiteis_sample.account.AccountService;
import com.heroku.shiiinykt_securiteis_sample.utils.ViewUtil;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.StringUtils;

public class LoginController {
	
	private static String NAME = "name";
	private static String MESSAGE = "message";
	
	@Inject
	private static AccountService service;

	public static Route index = (Request req, Response res) -> {
		Map<String, Object> attribute = AttributeFactory.create(req);

		return ViewUtil.render(attribute, Meta.Template.LOGIN);
	};
	
	public static Route indexHandler = (Request req, Response res) -> {
		Map<String, Object> attribute = AttributeFactory.create(req);

		Account account = service.find(req.queryParams(Meta.Parameter.NAME), req.queryParams(Meta.Parameter.PASSWORD));

		if (account != null) {
			req.session(true).attribute(Meta.Parameter.ACCOUNT, account);
			
			String redirect = Meta.URL.INDEX;
			if (StringUtils.isNotEmpty(req.queryParams(Meta.Parameter.REDIRECT))) {
				redirect = req.queryParams(Meta.Parameter.REDIRECT);
			}
			
			res.redirect(redirect, 301);
		}
		
		attribute.put(NAME, req.queryParams(Meta.Parameter.NAME));
		attribute.put(MESSAGE, Boolean.TRUE);
		
		return ViewUtil.render(attribute, Meta.Template.LOGIN);
	};
}
