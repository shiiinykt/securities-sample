package com.heroku.shiiinykt_securiteis_sample.line.impl;


import java.io.IOException;
import java.util.Date;

import com.heroku.shiiinykt_securiteis_sample.line.LineInfo;
import com.heroku.shiiinykt_securiteis_sample.line.LineInfoDao;
import com.heroku.shiiinykt_securiteis_sample.line.LineService;
import com.heroku.shiiinykt_securiteis_sample.utils.KeyGenUtil;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;

import lombok.SneakyThrows;

public class LineServiceImpl implements LineService {

	@Override
	public LineInfo find(String userId) {
		LineInfoDao dao = new LineInfoDao();
		return dao.find(userId);
	}

	@Override
	public LineInfo findByAccountId(String accountId) {
		LineInfoDao dao = new LineInfoDao();
		return dao.findByAccountId(accountId);
	}
	
	@Override
	public LineInfo findByCode(String code) {
		LineInfoDao dao = new LineInfoDao();
		return dao.findByCode(code);
	}
	
	@Override
	public String registory(String userId) {
		LineInfoDao dao = new LineInfoDao();
		LineInfo info = new LineInfo(userId);
		
		String code = KeyGenUtil.getCode();
		
		while(dao.countByCode(code) != 0) {
			code = KeyGenUtil.getCode();
		}
		
		info.setCode(code);
		dao.store(info);
		
		return code;
	}

	@Override
	public void activate(LineInfo info) {
		LineInfoDao dao = new LineInfoDao();
		
		info.setModified(new Date());
		info.setStatus(LineInfo.ACTICVE);
		info.setCode(null);
		
		dao.store(info);
	}

	@SneakyThrows(IOException.class)
	@Override
	public void pushMessage(PushMessage pushMessage) {
		LineMessagingServiceBuilder
		.create(System.getenv("CHANNEL_ACCESS_TOKEN"))
		.build()
		.pushMessage(pushMessage)
		.execute();
		
	}

	@SneakyThrows(IOException.class)
	@Override
	public void replyMessage(ReplyMessage replyMessage) {
		LineMessagingServiceBuilder
		.create(System.getenv("CHANNEL_ACCESS_TOKEN"))
		.build()
		.replyMessage(replyMessage)
		.execute();
	}

	@Override
	public void inactivate(String userId) {
		LineInfoDao dao = new LineInfoDao();
		LineInfo info = dao.find(userId);
		
		info.setModified(new Date());
		info.setStatus(LineInfo.INACTIVE);
		
		dao.store(info);		
	}

}
