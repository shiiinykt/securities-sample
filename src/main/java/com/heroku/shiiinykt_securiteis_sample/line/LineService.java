package com.heroku.shiiinykt_securiteis_sample.line;

import com.google.inject.ImplementedBy;
import com.heroku.shiiinykt_securiteis_sample.line.impl.LineServiceImpl;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;

@ImplementedBy(LineServiceImpl.class)
public interface LineService {
	
	public LineInfo find(String userId);
	
	public LineInfo findByAccountId(String accountId);
	
	public LineInfo findByCode(String code);
	
	public String registory(String userId);
	
	public void activate(LineInfo info);
	
	public void pushMessage(PushMessage pushMessage);
	
	public void replyMessage(ReplyMessage replyMessage);
	
	public void inactivate(String userId);
	
	public boolean checkActiveUser(String userId);
	
}
