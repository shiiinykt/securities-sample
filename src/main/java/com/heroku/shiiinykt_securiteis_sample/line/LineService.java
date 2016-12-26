package com.heroku.shiiinykt_securiteis_sample.line;

import com.google.inject.ImplementedBy;
import com.heroku.shiiinykt_securiteis_sample.line.impl.LineServiceImpl;

@ImplementedBy(LineServiceImpl.class)
public interface LineService {
	
	public LineInfo find(String userId);
	
	public LineInfo findByCode(String code);
	
	public String registory(String userId);
	
	public void activate(LineInfo info);
}
