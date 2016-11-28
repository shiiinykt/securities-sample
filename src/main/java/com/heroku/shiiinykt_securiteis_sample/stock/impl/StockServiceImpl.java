package com.heroku.shiiinykt_securiteis_sample.stock.impl;

import java.util.List;
import com.heroku.shiiinykt_securiteis_sample.stock.Stock;
import com.heroku.shiiinykt_securiteis_sample.stock.StockDao;
import com.heroku.shiiinykt_securiteis_sample.stock.StockService;

public class StockServiceImpl implements StockService {

	@Override
	public Stock find(String code) {
		StockDao dao = new StockDao();
		return dao.find(code);
	}

	@Override
	public List<Stock> search(String query) {
		StockDao dao = new StockDao();
		return dao.findByQuery(query);
	}

	@Override
	public void store(Stock stock) {
		StockDao dao = new StockDao();
		dao.store(stock);
	}

}
