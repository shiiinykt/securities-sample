package com.heroku.shiiinykt_securiteis_sample.callback;


import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.line.LineService;
import com.heroku.shiiinykt_securiteis_sample.stock.Stock;
import com.heroku.shiiinykt_securiteis_sample.stock.StockService;
import com.heroku.shiiinykt_securiteis_sample.utils.ViewUtil;
import com.heroku.shiiinykt_securities_sample.Meta;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.UnfollowEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;

import spark.Request;
import spark.Response;
import spark.Route;

public class CallbackController {

	@Inject
	private static CallbackService service;
	@Inject
	private static LineService lineService;
	@Inject
	private static StockService stockService;

	public static Route callback = (Request req, Response res) -> {

		try {
			CallbackRequest request = service.handle(req);
			request.getEvents().stream().forEach(event -> {
				if (event instanceof MessageEvent<?>) {
					if (((MessageEvent<?>) event).getMessage() instanceof TextMessageContent) {
						handleTextMessageEvent((MessageEvent<?>) event);
					}
					
				} else if (event instanceof FollowEvent) {
					handelFollowEvnet((FollowEvent) event);
					
				} else if (event instanceof UnfollowEvent) {
					handelUnFollowEvnet((UnfollowEvent) event);
				}
			});
		} catch (Exception e) {
			return ViewUtil.render(new Callback("NG"));

		}

		return ViewUtil.render(new Callback("OK"));
	};

	private static void handleTextMessageEvent(MessageEvent<?> event) {
		TextMessageContent content = (TextMessageContent) event.getMessage();
		
		/*TextMessage textMessage = new TextMessage(content.getText() + "!!");
		ReplyMessage replyMessage = new ReplyMessage(event.getReplyToken(), textMessage);
		*/
		
		List<Stock> stocks =  stockService.search(content.getText());
		List<Action> actions = new ArrayList<Action>();
		
		for (int i = 0; i < Math.min(stocks.size(), 4); i++) {
			actions.add(new PostbackAction(stocks.get(i).getName(), "action=" + stocks.get(i).getName()));
		}
		
		TemplateMessage templateMessage = new TemplateMessage("This is buttons template", 
				new ButtonsTemplate("https://www.misedas.net/item_images/item_group/l/6066/1311.jpg", content.getText(), content.getText(), actions));
		ReplyMessage replyMessage = new ReplyMessage(event.getReplyToken(), templateMessage);
		
		lineService.replyMessage(replyMessage);
		
	}
	
	private static void handelFollowEvnet(FollowEvent event) {
		String code = lineService.registory(event.getSource().getUserId());
		
		TextMessage textMessage = new TextMessage(Meta.URL.APP_URL + Meta.URL.LINE_REGISTORY + "?" + Meta.Parameter.CODE + "=" + code);
		ReplyMessage replyMessage = new ReplyMessage(event.getReplyToken(), textMessage);
		lineService.replyMessage(replyMessage);
	}
	
	private static void handelUnFollowEvnet(UnfollowEvent event) {
		lineService.inactivate(event.getSource().getUserId());
	}
}
