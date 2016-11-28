package com.heroku.shiiinykt_securities_sample;

import com.iciql.Db;


public class AbstractDao {
	
	protected Db db;
	
	private static String DB_URL = "jdbc:mysql://" + System.getenv("DB_URL") + ":3306/" + System.getenv("DB_DATABASE")  + "?useUnicode=yes&characterEncoding=UTF-8";
	private static String DB_USER = System.getenv("DB_USER");
	private static String DB_PASSWORD = System.getenv("DB_PASSWORD");
	
	protected Db open() {
		return Db.open(DB_URL, DB_USER, DB_PASSWORD);
	}
	
}
