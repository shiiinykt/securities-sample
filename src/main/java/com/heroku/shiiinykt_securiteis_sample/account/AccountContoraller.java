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

	private static String ID = "id";
	private static String NAME = "name";
	private static String MESSAGE = "message";
	
	@Inject
	private static AccountService service;
	
	public static Route registory = (Request req, Response res) -> {
		Map<String, Object> attributes = AttributeFactory.create(req);
		
		
		return ViewUtil.render(attributes, Meta.Template.ACCOUNT_REGISTORY);
	};
	
	public static Route handleRegistory = (Request req, Response res) -> {
		Map<String, Object> attributes = AttributeFactory.create(req);
		
		if (!service.exist(req.queryParams(Meta.Parameter.ID))) {
			
			Account account = new Account();
			account.setAccountId(req.queryParams(Meta.Parameter.ID));
			account.setName(req.queryParams(Meta.Parameter.NAME));
			account.setPasswordWithHash(req.queryParams(Meta.Parameter.PASSWORD));
			account.setPinWithHash(req.queryParams(Meta.Parameter.PIN));
			
			service.store(account);
			
			res.redirect(Meta.URL.INDEX);
		}
		
		attributes.put(ID, req.queryParams(Meta.Parameter.ID));
		attributes.put(NAME, req.queryParams(Meta.Parameter.NAME));
		attributes.put(MESSAGE, true);
		
		return ViewUtil.render(attributes, Meta.Template.ACCOUNT_REGISTORY);
	};
}
