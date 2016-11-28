package com.heroku.shiiinykt_securities_sample;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.heroku.shiiinykt_securiteis_sample.account.Account;

import spark.Request;

public class AttributeFactory {
	
	private static String CONNECTIOPN = "_";
	
	private static String ACCOUNT_NAME = "accountName";
	
	public static Map<String, Object> create(Request req) {
		Map<String, Object> attribute = new HashMap<>();
		setPathValues(attribute);
		
		if(req.session().attribute(Meta.Parameter.ACCOUNT) != null) {
			String accountName = ((Account) req.session().attribute(Meta.Parameter.ACCOUNT)).getName();
			attribute.put(ACCOUNT_NAME, accountName);
		}
		
		return attribute;
	}

	private static void setPathValues(Map<String, Object>  attribute) {
		Stream.of(Meta.class.getClasses()).forEach(ic -> {
			Stream.of(ic.getFields()).forEach(f -> {
				try {
					attribute.put(Meta.class.getSimpleName() 
							+ CONNECTIOPN + ic.getSimpleName() 
							+ CONNECTIOPN + f.getName(), f.get(ic));
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
			});
		});
	}

}
