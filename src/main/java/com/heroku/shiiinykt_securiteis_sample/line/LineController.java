package com.heroku.shiiinykt_securiteis_sample.line;

import java.util.Map;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.account.Account;
import com.heroku.shiiinykt_securiteis_sample.utils.ViewUtil;
import com.heroku.shiiinykt_securities_sample.AttributeFactory;
import com.heroku.shiiinykt_securities_sample.Meta;

import spark.Request;
import spark.Response;
import spark.Route;

public class LineController {
	
	@Inject
	private static LineService service;
	
	public static Route registory = (Request req, Response res) -> {
		Map<String, Object> attributes = AttributeFactory.create(req);
		
		String code = req.queryParams(Meta.Parameter.CODE);
		
		LineInfo info = service.findByCode(code);
		info.setAccountId(((Account) req.session().attribute(Meta.Parameter.ACCOUNT)).getAccountId());
		
		return ViewUtil.render(attributes, Meta.Template.LINE_REGISTORY);
	};

}
