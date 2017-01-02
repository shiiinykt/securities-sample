package com.heroku.shiiinykt_securiteis_sample.account;

import com.google.inject.ImplementedBy;
import com.heroku.shiiinykt_securiteis_sample.account.impl.AccountServiceImpl;

@ImplementedBy(AccountServiceImpl.class)
public interface AccountService {

	public Account find(String accountId, String password);
	
	public boolean checkPin(String accountId, String pin);
	
	public void store(Account account);
	
	public boolean exist(String accountId);
	
}
