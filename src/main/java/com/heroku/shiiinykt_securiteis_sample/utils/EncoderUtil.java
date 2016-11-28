package com.heroku.shiiinykt_securiteis_sample.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncoderUtil {

	private static String CHAR_SET = "UTF-8";
	
	public static String urlEncode(String value) {
		try {
			return URLEncoder.encode(value, CHAR_SET);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	public static String urlDecode(String value) {
		try {
			return URLDecoder.decode(value, CHAR_SET);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
}
