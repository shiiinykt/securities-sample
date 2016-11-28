package com.heroku.shiiinykt_securiteis_sample;



import com.heroku.shiiinykt_securiteis_sample.utils.EncoderUtil;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.utils.StringUtils;

public class Filters {
	
	public static Filter auth = (Request req, Response res) -> {
		if(req.session().attribute(Meta.Parameter.ACCOUNT) == null) {
			
			String redirect="";
			if ("GET".equals(req.requestMethod())) {
				 String redirectUrl = req.uri();
				if (StringUtils.isNotEmpty(req.queryString())) {
					redirectUrl += "?" + req.queryString();
				}
				redirect = "?" + Meta.Parameter.REDIRECT + "=" + EncoderUtil.urlEncode(redirectUrl);
			}

			res.redirect(Meta.URL.LOGIN + redirect, 301);
		};
	};

}
