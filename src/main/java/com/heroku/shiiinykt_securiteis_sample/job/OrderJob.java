package com.heroku.shiiinykt_securiteis_sample.job;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.inject.Inject;
import com.heroku.shiiinykt_securiteis_sample.order.OrderService;
import com.heroku.shiiinykt_securiteis_sample.order.StockOrder;
import com.heroku.shiiinykt_securiteis_sample.stock.Stock;
import com.heroku.shiiinykt_securiteis_sample.stock.StockService;

public class OrderJob implements Job {

	@Inject
	private static OrderService service;
	@Inject
	private static StockService stockService;
	
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
		});
	}

}
