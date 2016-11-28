package com.heroku.shiiinykt_securiteis_sample.reference;


import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

import lombok.Data;

@Data
@IQTable
public class OrderReference {
	
	@IQColumn
	private String orderId;
	@IQColumn
	private int amount;
	@IQColumn
	private String orderType;
	@IQColumn
	private Double price;
	@IQColumn
	private String depositType;
	@IQColumn
	private String status;
	@IQColumn
	private String code;
	@IQColumn
	private String name;
	@IQColumn
	private String market;
	@IQColumn
	private double stockPrice;

}
