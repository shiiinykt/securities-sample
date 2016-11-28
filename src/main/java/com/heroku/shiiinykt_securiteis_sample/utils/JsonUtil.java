package com.heroku.shiiinykt_securiteis_sample.utils;

import com.google.gson.Gson;

public class JsonUtil {

	public static String toJson(Object obj) {
		 Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	public static <T> T fromJson(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
}
