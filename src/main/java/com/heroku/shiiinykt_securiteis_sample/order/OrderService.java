package com.heroku.shiiinykt_securiteis_sample.order;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.heroku.shiiinykt_securiteis_sample.order.impl.OrderServiceImpl;


@ImplementedBy(OrderServiceImpl.class)
public interface OrderService {

	public StockOrder find( String accountID, String orderId);
	
	public List<StockOrder> search(String accountId);
	
	public List<StockOrder> findAllOrder();
	
	public String store(StockOrder order);
	
	public void update(StockOrder order);
}
