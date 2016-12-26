package com.heroku.shiiinykt_securiteis_sample.account;

import org.apache.commons.codec.digest.DigestUtils;

import com.heroku.shiiinykt_securities_sample.AbstractDao;
import com.iciql.Db;

public class AccountDao extends AbstractDao{
	
	public Account find(String accountId, String passoword) {
		try (Db db = open()) {
			Account a = new Account();
			
			return db.from(a)
					.where(a.getAccountId()).is(accountId)
					.and(a.getPassword()).is(DigestUtils.sha256Hex(passoword))
					.selectFirst();
		}
	}
	
	public long countByPin(String accountId, String pin) {
		try (Db db = open()) {
			Account a = new Account();
			
			return db.from(a)
					.where(a.getAccountId()).is(accountId)
					.and(a.getPin()).is(DigestUtils.sha256Hex(pin))
					.selectCount();
		}
	}
	
	public void store(Account account) {
		try (Db db = open()) {
			db.insert(account);
		}
	}

}
