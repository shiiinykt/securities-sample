package com.heroku.shiiinykt_securiteis_sample.order.impl;

import java.util.Date;
import java.util.List;

import com.heroku.shiiinykt_securiteis_sample.order.OrderService;
import com.heroku.shiiinykt_securiteis_sample.order.StockOrder;
import com.heroku.shiiinykt_securiteis_sample.order.StockOrderDao;
import com.heroku.shiiinykt_securiteis_sample.utils.KeyGenUtil;

public class OrderServiceImpl implements OrderService {

	@Override
	public StockOrder find(String accountId, String orderId) {
		StockOrderDao dao = new StockOrderDao();
		return dao.find(accountId, orderId);
	}

	@Override
	public List<StockOrder> search(String accountId) {
		StockOrderDao dao = new StockOrderDao();
		return dao.findByAccountId(accountId);
	}

	@Override
	public String store(StockOrder order) {
		StockOrderDao dao = new StockOrderDao();
		String orderId = KeyGenUtil.getOrderId();
		
		while(dao.count(orderId) != 0) {
			orderId = KeyGenUtil.getOrderId();
		}
		
		order.setStatus(StockOrder.ORDER);
		order.setOrderId(orderId);
		order.setCreated(new Date());
		order.setModified(new Date());
		
		dao.store(order);
		
		return orderId;
	}

	@Override
	public List<StockOrder> findAllOrder() {
		StockOrderDao dao = new StockOrderDao();
		return dao.findByStatus();
	}

	@Override
	public void update(StockOrder order) {
		StockOrderDao dao = new StockOrderDao();
		dao.store(order);
	}
	
	

}
