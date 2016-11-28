package com.heroku.shiiinykt_securiteis_sample.stock;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.heroku.shiiinykt_securiteis_sample.stock.impl.StockServiceImpl;

@ImplementedBy(StockServiceImpl.class)
public interface StockService {

	public Stock find(String code);
	
	public List<Stock> search(String query);
	
	public void store(Stock stock);
	
}
