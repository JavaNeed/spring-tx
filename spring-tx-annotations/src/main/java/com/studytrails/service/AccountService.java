package com.studytrails.service;

import com.studytrails.model.Account;

public interface AccountService {
	/*
	 * public void insert(Account account);
	 * 
	 * public void update(Account account);
	 * 
	 * public Account select(String accountNumber);
	 */

	public void transferFunds(Account fromAccount, Account toAccount, Double transferAmount);

	public void transferFundsException(Account fromAccount, Account toAccount, Double transferAmount) throws Exception;
	
	public void createAccount(Account account); 
	
	public Account getAccount(String accountNumber);
}
