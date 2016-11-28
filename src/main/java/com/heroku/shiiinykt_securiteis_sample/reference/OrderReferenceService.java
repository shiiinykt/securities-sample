package com.heroku.shiiinykt_securiteis_sample.reference;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.heroku.shiiinykt_securiteis_sample.reference.impl.OrderReferenceServiceImpl;

@ImplementedBy(OrderReferenceServiceImpl.class)
public interface OrderReferenceService {
	
	public List<OrderReference> find(String accountId);

}
