package com.heroku.shiiinykt_securiteis_sample.order;

import java.net.URLEncoder;
import java.util.Map;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.AttributeFactory;
import com.heroku.shiiinykt_securiteis_sample.Meta;
import com.heroku.shiiinykt_securiteis_sample.account.Account;
import com.heroku.shiiinykt_securiteis_sample.account.AccountService;
import com.heroku.shiiinykt_securiteis_sample.stock.StockService;
import com.heroku.shiiinykt_securiteis_sample.utils.EncoderUtil;
import com.heroku.shiiinykt_securiteis_sample.utils.KeyGenUtil;
import com.heroku.shiiinykt_securiteis_sample.utils.ViewUtil;

import lombok.NonNull;
import spark.Request;
import spark.Response;
import spark.Route;


public class OrderController {

	private static String STOCK = "stock";
	private static String ORDER = "order";
	private static String TRANZACTION = "tranzaction";
	private static String ORDER_ID = "orderId";
	
	private static String PARAM_ORDER_VALUE = "OV_";
	private static String PARAM_ORDER_KEY = "OK_";
	
	@Inject
	private static OrderService service;
	@Inject
	private static StockService stockService;
	@Inject
	private static AccountService accountService;
	
	public static Route input = (Request req, Response res) -> {
		Map<String, Object> attributes = AttributeFactory.create(req);
		attributes.put(STOCK, stockService.find(req.queryParams(Meta.Parameter.CODE)));
		
		return ViewUtil.render(attributes, Meta.Template.ORDER_INPUT);
	};
	
	public static Route inputHandler = (Request req, Response res) -> {
		
		String accountId = ((Account) req.session().attribute(Meta.Parameter.ACCOUNT)).getAccountId();
		String code = req.queryParams(Meta.Parameter.CODE);
		int amount = Integer.valueOf(req.queryParams(Meta.Parameter.AMOUNT));
		String depositType = req.queryParams(Meta.Parameter.DEPOSIT_TYPE);
		String orderType = req.queryParams(Meta.Parameter.ORDER_TYPE);
		
		Double price = null;
		if (StockOrder.LIMIT.equals(orderType)) {
			price = Double.valueOf(req.queryParams(Meta.Parameter.PRICE));
		}
		
		StockOrder stockOrder = new StockOrder(accountId, code, amount, price, orderType, depositType);
		String transactionId = KeyGenUtil.getTransactionId();
		req.session().attribute(getParamOrderValue(transactionId), stockOrder);
		
		res.redirect(Meta.URL.ORDER_CONFIRM + "?" + Meta.Parameter.TRANZACTION + "=" + URLEncoder.encode(transactionId, "UTF-8"), 301);
		
		return null;
	};
	
	public static Route confirm = (Request req, Response res) -> {
		Map<String, Object> attributes = AttributeFactory.create(req);
		
		StockOrder order = (StockOrder) req.session().attribute(getParamOrderValue(req.queryParams(Meta.Parameter.TRANZACTION)));
		
		attributes.put(ORDER, order);
		attributes.put(STOCK, stockService.find(order.getCode()));
		attributes.put(TRANZACTION, req.queryParams(Meta.Parameter.TRANZACTION));
		
		return ViewUtil.render(attributes, Meta.Template.ORDER_CONFIRM);
	};
	
	public static Route confirmHandler = (Request req, Response res) -> {
		Account account = (Account) req.session().attribute(Meta.Parameter.ACCOUNT);
		
		if (accountService.checkPin(account.getAccountId(), req.queryParams(Meta.Parameter.PIN))) {
			StockOrder order = (StockOrder) req.session().attribute(getParamOrderValue(req.queryParams(Meta.Parameter.TRANZACTION)));
			req.session().removeAttribute(getParamOrderValue(req.queryParams(Meta.Parameter.TRANZACTION)));
			
			String orderId = service.store(order);
			req.session().attribute(getParamOderKey(req.queryParams(Meta.Parameter.TRANZACTION)), orderId);

			res.redirect(Meta.URL.ORDER_FINISH + "?" + Meta.Parameter.TRANZACTION + "=" + EncoderUtil.urlEncode(req.queryParams(Meta.Parameter.TRANZACTION)), 301);
		}

		return ViewUtil.render(null, Meta.Template.ORDER_CONFIRM);
	};
	
	public static Route finsh = (Request req, Response res) -> {
		Map<String, Object> attributes = AttributeFactory.create(req);
		
		@NonNull 
		String orderId = req.session().attribute(getParamOderKey(req.queryParams(Meta.Parameter.TRANZACTION)));
		req.session().removeAttribute(getParamOderKey(req.queryParams(Meta.Parameter.TRANZACTION)));
		
		attributes.put(ORDER_ID, orderId);
		
		return ViewUtil.render(attributes, Meta.Template.ORDER_FINISH);
	};
	
	private static String getParamOderKey(String tranzactionId) {
		return PARAM_ORDER_KEY + tranzactionId;
	}
	
	private static String getParamOrderValue(String tranzactionId) {
		return PARAM_ORDER_VALUE + tranzactionId;
	}
}
