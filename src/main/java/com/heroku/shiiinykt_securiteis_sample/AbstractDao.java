package com.heroku.shiiinykt_securiteis_sample;

import com.iciql.Db;


public class AbstractDao {
	
	protected Db db;
	
	private static String DB_URL = "jdbc:mysql://localhost:3306/securities_sample?useUnicode=yes&characterEncoding=UTF-8";
	private static String DB_USER = System.getenv("DB_USER");
	private static String DB_PASSWORD = System.getenv("DB_PASSWORD");
	
	protected Db open() {
		return Db.open(DB_URL, DB_USER, DB_PASSWORD);
	}
	
}
