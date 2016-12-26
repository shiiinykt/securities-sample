package com.heroku.shiiinykt_securiteis_sample.line;

import java.util.Date;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

import lombok.Data;

@Data
@IQTable
public class LineInfo {
	
	public static String PROGRESS = "progress";
	public static String ACTICVE = "active";
	public static String INACTIVE = "inactive";
	

	@IQColumn(name = "user_id", primaryKey = true, length = 64)
	private String userId;
	@IQColumn
	private String code;
	@IQColumn
	private String status;
	@IQColumn(name = "account_id")
	private String accountId;
	@IQColumn
	private Date created;
	@IQColumn
	private Date modified;
	
	public LineInfo() {
	}

	public LineInfo(String userId) {
		this.userId = userId;
		this.status = PROGRESS;
		this.created = new Date();
		this.modified = new Date();
		
	}
	
}
