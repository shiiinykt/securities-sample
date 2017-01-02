package com.heroku.shiiinykt_securiteis_sample.job;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.line.LineInfo;
import com.heroku.shiiinykt_securiteis_sample.line.LineService;
import com.heroku.shiiinykt_securiteis_sample.order.OrderService;
import com.heroku.shiiinykt_securiteis_sample.order.StockOrder;
import com.heroku.shiiinykt_securiteis_sample.stock.Stock;
import com.heroku.shiiinykt_securiteis_sample.stock.StockService;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TextMessage;

public class OrderJob implements Job {

	@Inject
	private static OrderService service;
	@Inject
	private static StockService stockService;
	@Inject
	private static LineService lineService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		List<StockOrder> list = service.findAllOrder();
		list.stream().forEach(so -> {
			if(StockOrder.MARKET.equals(so.getOrderType())) {
				Stock s = stockService.find(so.getCode());
				so.setPrice(s.getPrice());
			}
			so.setStatus(StockOrder.CONTRACT);
			so.setModified(new Date());
			
			service.update(so);
			
			LineInfo info = lineService.findByAccountId(so.getAccountId());
			if (info != null) {
			
				TextMessage textMessage = new TextMessage(so.toString());
				PushMessage pushMessage = new PushMessage(info.getUserId(), textMessage);
				lineService.pushMessage(pushMessage);
			}
		});
	}

}
