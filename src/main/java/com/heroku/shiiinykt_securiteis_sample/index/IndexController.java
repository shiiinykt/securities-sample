package com.heroku.shiiinykt_securiteis_sample.index;

import java.util.Map;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.stock.StockService;
import com.heroku.shiiinykt_securiteis_sample.utils.ViewUtil;
import com.heroku.shiiinykt_securities_sample.AttributeFactory;
import com.heroku.shiiinykt_securities_sample.Meta;

import spark.Request;
import spark.Response;
import spark.Route;

public class IndexController {

	private static String QUERY = "query";
	private static String STOCKS = "stocks";
	
	@Inject
	private static StockService service;

	public static Route index = (Request req, Response res) -> {
		Map<String, Object> attributes = AttributeFactory.create(req);

		String query = req.queryParams(Meta.Parameter.QUERY);
		
		attributes.put(QUERY, query);
		attributes.put(STOCKS, service.search(query));
		
		return ViewUtil.render(attributes, Meta.Template.INDEX);
	};

}
