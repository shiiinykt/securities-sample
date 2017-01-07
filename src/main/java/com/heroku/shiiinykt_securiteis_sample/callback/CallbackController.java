package com.heroku.shiiinykt_securiteis_sample.callback;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.line.LineService;
import com.heroku.shiiinykt_securiteis_sample.order.OrderService;
import com.heroku.shiiinykt_securiteis_sample.order.StockOrder;
import com.heroku.shiiinykt_securiteis_sample.stock.Stock;
import com.heroku.shiiinykt_securiteis_sample.stock.StockService;
import com.heroku.shiiinykt_securiteis_sample.utils.ViewUtil;
import com.heroku.shiiinykt_securities_sample.Meta;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.UnfollowEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import java.util.concurrent.TimeUnit;

import spark.Request;
import spark.Response;
import spark.Route;

public class CallbackController {
	private static String ORDER = "order";
	
	@Inject
	private static CallbackService service;
	@Inject
	private static LineService lineService;
	@Inject
	private static StockService stockService;
	@Inject
	private static OrderService orderService;
	
	
	private static Cache<String, LineSession> lineSessionStore = new Cache2kBuilder<String, LineSession>() {}
												.expireAfterWrite(5, TimeUnit.MINUTES)
												.build();

	public static Route callback = (Request req, Response res) -> {

		try {
			CallbackRequest request = service.handle(req);
			request.getEvents().stream().forEach(event -> {
				if (event instanceof FollowEvent) {
					handelFollowEvnet((FollowEvent) event);
				
				} else if (event instanceof UnfollowEvent) {
					handelUnFollowEvnet((UnfollowEvent) event);
				}
				
				////ORDER EVENT/////
				String action = lineSession(event).getAction();
				if (LineSession.PROCESS_BEGIN.equals(action)) {
					processBegin(event);
				} else if (LineSession.PROCESS_SET_CODE.equals(action)) {
					processSetCode(event);
				} else if (LineSession.PROCESS_SET_AMMOUNT.equals(action)) {
					processSetAmount(event);
				} else if (LineSession.PROCESS_SET_ORDER_TYPE.equals(action)) {
					processSetOrderType(event);
				} else if (LineSession.PROCESS_SET_PRICE.equals(action)) {
					processSetPrice(event);
				} else if (LineSession.PROCESS_SET_DEPOSIT_TYPE.equals(action)) {
					processSetDepositType(event);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return ViewUtil.render(new Callback("NG"));

		}

		return ViewUtil.render(new Callback("OK"));
	};
	
	public static LineSession lineSession(Event event) {
		String userId = event.getSource().getUserId();
		
		if(!lineSessionStore.containsKey(userId)) {
			lineSessionStore.put(userId, new LineSession());
		}
		
		
		return lineSessionStore.get(userId);
	}
	
	private static void processBegin(Event event) {
		StockOrder order = new StockOrder();
		order.setAccountId(lineService.find(event.getSource().getUserId()).getAccountId());
		lineSession(event).attribute(ORDER, order);
		
		changeSetCode(event);

	};

	private static void changeSetCode(Event event) {
		lineSession(event).setAction(LineSession.PROCESS_SET_CODE);
		
		TextMessage text = new TextMessage("銘柄コードまはた銘柄名を入力してください。");
		PushMessage message = new PushMessage(event.getSource().getUserId(), text);
		lineService.pushMessage(message);

	}
	
	private static void processSetCode(Event event) {
		
		if (event instanceof MessageEvent<?> 
		&& ((MessageEvent<?>) event).getMessage() instanceof TextMessageContent) {
			TextMessageContent content = (TextMessageContent) ((MessageEvent<?>) event).getMessage();
			
			List<Stock> stocks =  stockService.search(content.getText());
			List<Action> actions = new ArrayList<Action>();
			
			for (int i = 0; i < Math.min(stocks.size(), 4); i++) {
				actions.add(new PostbackAction(stocks.get(i).getCode() + "：" + stocks.get(i).getName(),stocks.get(i).getCode()));
			}
			
			TemplateMessage templateMessage = new TemplateMessage("銘柄検索",
				new ButtonsTemplate(null, "銘柄", "銘柄をお選びください。", actions));
			ReplyMessage replyMessage = new ReplyMessage(((MessageEvent<?>) event).getReplyToken(), templateMessage);

			lineService.replyMessage(replyMessage);
		
		} else if (event instanceof PostbackEvent) {
			StockOrder order = lineSession(event).attribute(ORDER);
			order.setCode(((PostbackEvent) event).getPostbackContent().getData());
			
			changeSetAmount(event);
		}
	}
	
	private static void changeSetAmount(Event event) {
		lineSession(event).setAction(LineSession.PROCESS_SET_AMMOUNT);

		TextMessage text = new TextMessage("注文数量を入力してください。");
		PushMessage message = new PushMessage(event.getSource().getUserId(), text);
		lineService.pushMessage(message);
	}
	
	@SuppressWarnings("unchecked")
	private static void processSetAmount(Event event) {
		if (event instanceof MessageEvent<?> 
		&& ((MessageEvent<?>) event).getMessage() instanceof TextMessageContent
		&& StringUtils.isNumeric(((MessageEvent<TextMessageContent>) event).getMessage().getText())) {
			
			StockOrder order = lineSession(event).attribute(ORDER);
			order.setAmount(Integer.valueOf(((MessageEvent<TextMessageContent>) event).getMessage().getText()));

			changeSetOrderType(event);
		}
	}
	
	private static void changeSetOrderType(Event event) {
		lineSession(event).setAction(LineSession.PROCESS_SET_ORDER_TYPE);
		
		List<Action> actions = new ArrayList<Action>();
		actions.add(new PostbackAction("成行", StockOrder.MARKET));
		actions.add(new PostbackAction("指値", StockOrder.LIMIT));
		
		TemplateMessage templateMessage = new TemplateMessage("注文区分選択",
			new ButtonsTemplate(null, "注文区分", "注文区分をお選びください。：", actions));
		PushMessage pushMessage = new PushMessage(event.getSource().getUserId(), templateMessage);

		lineService.pushMessage(pushMessage);

	}

	private static void processSetOrderType(Event event) {
		if (event instanceof PostbackEvent) {
			StockOrder order = lineSession(event).attribute(ORDER);
			order.setOrderType(((PostbackEvent) event).getPostbackContent().getData());
			
			if (StockOrder.MARKET.equals(order.getOrderType())) {
				changeSetDepositType(event);
			} else {
				changeSetPrice(event);
			}
		}
	}
	
	private static void changeSetPrice(Event event) {
		lineSession(event).setAction(LineSession.PROCESS_SET_PRICE);

		TextMessage text = new TextMessage("価格を入力してください。");
		PushMessage message = new PushMessage(event.getSource().getUserId(), text);
		lineService.pushMessage(message);
	}
	
	@SuppressWarnings("unchecked")
	private static void processSetPrice(Event event) {
		if (event instanceof MessageEvent<?> 
		&& ((MessageEvent<?>) event).getMessage() instanceof TextMessageContent
		&& isDecimal(((MessageEvent<TextMessageContent>) event).getMessage().getText())) {
			
			StockOrder order = lineSession(event).attribute(ORDER);
			order.setPrice(Double.valueOf(((MessageEvent<TextMessageContent>) event).getMessage().getText()));
			
			lineSession(event).setAction(LineSession.PROCESS_SET_DEPOSIT_TYPE);
			
			changeSetDepositType(event);
		}
	}
	
	private static boolean isDecimal(String value) {
		try {
			Double.valueOf(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private static void changeSetDepositType(Event event) {
		lineSession(event).setAction(LineSession.PROCESS_SET_DEPOSIT_TYPE);

		List<Action> actions = new ArrayList<Action>();
		actions.add(new PostbackAction("特定", StockOrder.SPECIFIC));
		actions.add(new PostbackAction("一般", StockOrder.GENERAL));
		actions.add(new PostbackAction("NISA", StockOrder.NISA));
		
		TemplateMessage templateMessage = new TemplateMessage("預り区分選択",
			new ButtonsTemplate(null, "預り区分", "預り区分をお選びください。", actions));
		PushMessage pushMessage = new PushMessage(event.getSource().getUserId(), templateMessage);

		lineService.pushMessage(pushMessage);
	}
	
	private static void processSetDepositType(Event event) {
		if (event instanceof PostbackEvent) {
			StockOrder order = lineSession(event).attribute(ORDER);
			order.setDepositType(((PostbackEvent) event).getPostbackContent().getData());
			
			processCommit(event);
		}
	}

	private static void processCommit(Event event) {
		orderService.store(lineSession(event).attribute(ORDER));
		
		lineSession(event).removeAttribute(ORDER);
		
		lineSession(event).setAction(LineSession.PROCESS_BEGIN);
		
		TextMessage text = new TextMessage("注文が完了しました。");
		PushMessage message = new PushMessage(event.getSource().getUserId(), text);
		lineService.pushMessage(message);
		
	};
	
	private static void handelFollowEvnet(FollowEvent event) {
		String code = lineService.registory(event.getSource().getUserId());
		
		TextMessage textMessage = new TextMessage(Meta.URL.APP_URL + Meta.URL.LINE_REGISTORY + "?" + Meta.Parameter.CODE + "=" + code);
		ReplyMessage replyMessage = new ReplyMessage(event.getReplyToken(), textMessage);
		lineService.replyMessage(replyMessage);
	}
	
	private static void handelUnFollowEvnet(UnfollowEvent event) {
		lineService.inactivate(event.getSource().getUserId());
	}
	
	public static class LineSession {
		public static String PROCESS_BEGIN = "pb";
		public static String PROCESS_SET_CODE = "psc";
		public static String PROCESS_SET_AMMOUNT = "psa";
		public static String PROCESS_SET_ORDER_TYPE = "pso";
		public static String PROCESS_SET_PRICE = "psp";
		public static String PROCESS_SET_DEPOSIT_TYPE = "psd";
		public static String PROCESS_COMMIT = "pc";
		
		private String action = PROCESS_BEGIN;
		private Map<String, Object> attributes;
		
		LineSession() {
			attributes = new HashMap<String, Object>();
		}
		
		public String getAction() {
			return action;
		}
		
		public void setAction(String action) {
			this.action = action;
		}
		
		@SuppressWarnings("unchecked")
		public <T> T attribute(String key) {
			return (T) attributes.get(key);
		}
		
		public void attribute(String key, Object value) {
			attributes.put(key, value);
		}
		
		public void removeAttribute(String key) {
			attributes.remove(key);
		}
	}
}
