package com.heroku.shiiinykt_securiteis_sample.account;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.iciql.Iciql.IQColumn;
import com.iciql.Iciql.IQTable;

import lombok.Data;

@Data
@IQTable
public class Account {

	
	@IQColumn(name="account_id", primaryKey=true, length=64)
	private String accountId;
	@IQColumn
	private String name;
	@IQColumn
	private String password;
	@IQColumn
	private String pin;
	@IQColumn
	private Date created;
	@IQColumn
	private Date modified;
	
	
	public void setPasswordWithHash(String password) {
		this.password = DigestUtils.sha256Hex(password);
	}
	
	public void setPinWithHash(String pin) {
		this.pin = DigestUtils.sha256Hex(pin);
	}

}
