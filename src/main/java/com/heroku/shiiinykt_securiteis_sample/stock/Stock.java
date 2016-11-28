package com.heroku.shiiinykt_securiteis_sample.stock;


import java.util.Date;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

import lombok.Data;

@Data
@IQTable
public class Stock {

	@IQColumn(primaryKey=true, length=16)
	private String code;
	@IQColumn
	private String name;
	@IQColumn
	private String market;
	@IQColumn
	private double price;
	@IQColumn
	private int unit;
	@IQColumn(name="previous_price")
	private double previousPrice;
	@IQColumn(name="max_price")
	private double maxPrice;
	@IQColumn(name="min_price")
	private double minPrice;
	@IQColumn
	boolean topix100;
	@IQColumn
	private Date created;
	@IQColumn
	private Date modified;
	
	public Double getPreviousRatio() {
		if (previousPrice != .0) {
			return (price - previousPrice) / previousPrice;
		}
		
		return null;
	}
}
