package com.heroku.shiiinykt_securiteis_sample.order;

import java.util.List;

import com.heroku.shiiinykt_securities_sample.AbstractDao;
import com.iciql.Db;

public class StockOrderDao extends AbstractDao {


	public StockOrder find(String accountId, String orderId) {
		try (Db db = open()) {
			StockOrder s = new StockOrder();
			
			return db.from(s)
					.where(s.getOrderId()).is(orderId)
					.and(s.getAccountId()).is(accountId)
					.selectFirst();
		}
	}
	
	public List<StockOrder> findByAccountId(String accountId) {
		try (Db db = open()) {
			StockOrder s = new StockOrder();

			return db.from(s)
					.where(s.getAccountId()).is(accountId)
					.orderByDesc(s.getCreated())
					.select();
		}
	}
	
	public List<StockOrder> findByStatus() {
		try (Db db = open()) {
			StockOrder s = new StockOrder();

			return db.from(s)
					.where(s.getStatus()).is(StockOrder.ORDER)
					.select();
		}
	}
	
	
	public void store(StockOrder stockOrder) {
		try (Db db = open()) {
			db.merge(stockOrder);
		}
	}
	
	public long count(String orderId) {
		try (Db db = open()) {
			StockOrder s = new StockOrder();
			
			return db.from(s)
					.where(s.getOrderId()).is(orderId)
					.selectCount();
		}
	}
}
