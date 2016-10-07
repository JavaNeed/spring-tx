package com.studytrails.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.studytrails.dao.AccountDAO;
import com.studytrails.model.Account;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDAO;
	
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void transferFunds(Account fromAccount, Account toAccount, Double transferAmount) {
		fromAccount.debit(transferAmount);
		toAccount.credit(transferAmount);
		accountDAO.update(fromAccount);
		accountDAO.update(toAccount);

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	@Override
	public void transferFundsException(Account fromAccount, Account toAccount, Double transferAmount) throws Exception {
		fromAccount.debit(transferAmount);
		toAccount.credit(transferAmount);
		
		accountDAO.update(fromAccount);
		accountDAO.update(toAccount);

		// Simulate an exception that occurs during funds transfer
		throw new Exception();

	}

	@Override
	public void createAccount(Account account) {
		accountDAO.insert(account);
	}

	@Override
	public Account getAccount(String accountNumber) {
		return accountDAO.select(accountNumber);
	}

}
