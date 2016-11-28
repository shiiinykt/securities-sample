package com.heroku.shiiinykt_securiteis_sample.stock;


import java.util.List;

import com.heroku.shiiinykt_securiteis_sample.AbstractDao;
import com.iciql.Db;

public class StockDao extends AbstractDao{

	public Stock find(String code) {
		try (Db db = open()) {
			Stock s = new Stock();
			
			return db.from(s)
					.where(s.getCode()).is(code)
					.selectFirst();
		}
	}
	
	public List<Stock> findByQuery(String query) {
		try (Db db = open()) {
			Stock s = new Stock();

			return db.from(s)
					.where(s.getCode()).like("%" + query + "%")
					.or(s.getName()).like("%" + query + "%")
					.orderBy(s.getCode())
					.select();
		}
	}
	
	public void store(Stock stock) {
		try (Db db = open()) {
			db.insert(stock);
		}
	}
	
}
