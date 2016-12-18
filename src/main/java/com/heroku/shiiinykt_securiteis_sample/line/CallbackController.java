package com.heroku.shiiinykt_securiteis_sample.line;

import java.io.IOException;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.utils.ViewUtil;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.CallbackRequest;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;

import spark.Request;
import spark.Response;
import spark.Route;

public class CallbackController {

	@Inject
	private static CallbackService service;

	public static Route callback = (Request req, Response res) -> {

		try {
			CallbackRequest request = service.handle(req);
			request.getEvents().stream().forEach(event -> {
				if (event instanceof MessageEvent<?>) {
					if (((MessageEvent<?>) event).getMessage() instanceof TextMessageContent) {
						handleTextMessageEvent((MessageEvent<?>) event);
					}
				}
			});
		} catch (Exception e) {
			return ViewUtil.render(new Callback("NG"));

		}

		return ViewUtil.render(new Callback("OK"));
	};

	private static void handleTextMessageEvent(MessageEvent<?> event) {

		try {
			TextMessageContent content = (TextMessageContent) event.getMessage();
	
			TextMessage textMessage = new TextMessage(content.getText() + "!!");
			ReplyMessage replyMessage = new ReplyMessage(event.getReplyToken(), textMessage);
			LineMessagingServiceBuilder
						.create(System.getenv("CHANNEL_ACCESS_TOKEN"))
						.build()
						.replyMessage(replyMessage)
						.execute();
			
		} catch (IOException e) {
		}
		
	}
}
