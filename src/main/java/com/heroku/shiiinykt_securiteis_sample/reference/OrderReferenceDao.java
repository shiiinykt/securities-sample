package com.heroku.shiiinykt_securiteis_sample.reference;

import java.util.List;

import com.heroku.shiiinykt_securiteis_sample.AbstractDao;
import com.heroku.shiiinykt_securiteis_sample.order.StockOrder;
import com.heroku.shiiinykt_securiteis_sample.stock.Stock;
import com.iciql.Db;

public class OrderReferenceDao extends AbstractDao {
	
	public List<OrderReference> find(String accountId) {
		try (Db db = open()) {
			StockOrder so = new StockOrder();
			Stock s = new Stock();
			
			return db.from(so).innerJoin(s).on(so.getCode()).is(s.getCode())
					.where(so.getAccountId()).is(accountId)
					.orderBy(so.getCode())
					.select(new OrderReference() {{
						setAmount(so.getAmount());
						setCode(so.getCode());
						setDepositType(so.getDepositType());
						setMarket(s.getMarket());
						setName(s.getName());
						setOrderId(so.getOrderId());
						setOrderType(so.getOrderType());
						setPrice(so.getPrice());
						setStatus(so.getStatus());
						setStockPrice(s.getPrice());
					}});
		}
	}
	

}
