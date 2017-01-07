package com.heroku.shiiinykt_securiteis_sample.order;

import java.util.Date;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

import lombok.Data;

@Data
@IQTable(name = "stock_order")
public class StockOrder {
	public static String ORDER = "order";
	public static String CANCEL = "cancel";
	public static String CONTRACT = "contract";
	
	public static String MARKET = "market";
	public static String LIMIT = "limit";
	
	public static String SPECIFIC = "specific";
	public static String GENERAL = "general";
	public static String NISA = "nisa";
	

	@IQColumn(name = "order_id", primaryKey = true, length = 128)
	private String orderId;
	@IQColumn(name = "account_id")
	private String accountId;
	@IQColumn
	private String code;
	@IQColumn
	private Integer amount;
	@IQColumn(name = "order_type")
	private String orderType;
	@IQColumn
	private Double price;
	@IQColumn(name = "deposit_type")
	private String depositType;
	@IQColumn
	private String status;
	@IQColumn
	private Date created;
	@IQColumn
	private Date modified;

	public StockOrder() {
	};

	public StockOrder(
				String accountId,
				String code,
				Integer amount,
				Double price,
				String orderType,
				String depositType) {

		this.accountId = accountId;
		this.code = code;
		this.amount = amount;
		this.price = price;
		this.price = price;
		this.orderType = orderType;
		this.depositType = depositType;
	}


}
