package com.heroku.shiiinykt_securiteis_sample.reference.impl;

import java.util.List;

import com.heroku.shiiinykt_securiteis_sample.reference.OrderReference;
import com.heroku.shiiinykt_securiteis_sample.reference.OrderReferenceDao;
import com.heroku.shiiinykt_securiteis_sample.reference.OrderReferenceService;

public class OrderReferenceServiceImpl implements OrderReferenceService {

	@Override
	public List<OrderReference> find(String accountId) {
		OrderReferenceDao dao = new OrderReferenceDao();
		return dao.find(accountId);
	}

}
