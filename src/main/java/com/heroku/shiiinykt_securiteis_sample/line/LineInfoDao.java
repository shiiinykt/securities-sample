package com.heroku.shiiinykt_securiteis_sample.line;

import com.heroku.shiiinykt_securities_sample.AbstractDao;
import com.iciql.Db;

public class LineInfoDao extends AbstractDao {

	public LineInfo find(String userId) {
		try (Db db = open()) {
			LineInfo l = new LineInfo();
			
			return db.from(l)
					.where(l.getUserId()).is(userId)
					.selectFirst();
		}
	}

	public LineInfo findByAccountId(String accountId) {
		try (Db db = open()) {
			LineInfo l = new LineInfo();
			
			return db.from(l)
					.where(l.getAccountId()).is(accountId)
					.and(l.getStatus()).is(LineInfo.ACTICVE)
					.selectFirst();
		}
	}
	
	public LineInfo findByCode(String code) {
		try (Db db = open()) {
			LineInfo l = new LineInfo();
			
			return db.from(l)
					.where(l.getCode()).is(code)
					.selectFirst();
		}
	}
	
	public long countByCode(String code) {
		try (Db db = open()) {
			LineInfo l = new LineInfo();
			
			return db.from(l)
					.where(l.getCode()).is(code)
					.selectCount();
		}
	}

	public void store(LineInfo info) {
		try (Db db = open()) {
			db.merge(info);
		}
	}
}
