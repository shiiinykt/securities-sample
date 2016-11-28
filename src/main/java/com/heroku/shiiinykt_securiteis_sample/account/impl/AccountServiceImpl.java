package com.heroku.shiiinykt_securiteis_sample.account.impl;

import com.heroku.shiiinykt_securiteis_sample.account.Account;
import com.heroku.shiiinykt_securiteis_sample.account.AccountDao;
import com.heroku.shiiinykt_securiteis_sample.account.AccountService;

public class AccountServiceImpl implements AccountService {

	@Override
	public Account find(String accountId, String password) {
		AccountDao dao = new AccountDao();
		return dao.find(accountId, password);
	}

	@Override
	public boolean checkPin(String accountId, String pin) {
		AccountDao dao = new AccountDao();
		return dao.countByPin(accountId, pin) == 1L;
	}
	
	@Override
	public void store(Account account) {
		AccountDao dao = new AccountDao();
		dao.store(account);
	}

}
