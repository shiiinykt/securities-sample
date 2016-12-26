package com.heroku.shiiinykt_securiteis_sample.utils;

import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class KeyGenUtil {
	
	public static String getTransactionId() {
		return RandomStringUtils.randomAlphanumeric(8);
	}
	
	public static String getOrderId() {
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	public static String getCode() {
		return RandomStringUtils.randomAlphanumeric(8);
	}

}
