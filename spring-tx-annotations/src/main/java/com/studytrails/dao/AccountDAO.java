package com.studytrails.dao;

import com.studytrails.model.Account;

public interface AccountDAO {
	public void insert(Account account);

	public void update(Account account);

	public Account select(String accountNumber);
}
