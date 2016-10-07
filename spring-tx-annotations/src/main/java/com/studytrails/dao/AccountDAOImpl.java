package com.studytrails.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.studytrails.model.Account;

public class AccountDAOImpl implements AccountDAO {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insert(Account account) {
		String insertSql = "INSERT INTO ACCOUNT (number, balance) VALUES(?,?);";

		jdbcTemplate.update(insertSql, new Object[] { account.getNumber(), account.getBalance() });
	}

	@Override
	public void update(Account account) {
		String updateSql = "UPDATE ACCOUNT SET balance = ? where number = ?;";

		jdbcTemplate.update(updateSql, new Object[] { account.getBalance(), account.getNumber() });
	}

	@Override
	public Account select(String accountNumber) {
		String selectSql = "SELECT * FROM ACCOUNT WHERE number = ?;";

		Account account = jdbcTemplate.queryForObject(selectSql, new Object[]{accountNumber},this::mapAccount);
		return account;
	}

	private Account mapAccount(ResultSet resultSet, int row) throws SQLException {
		Account account = new Account();
		account.setId(resultSet.getInt("id"));
		account.setNumber(resultSet.getString("number"));
		account.setBalance(resultSet.getDouble("balance"));
		
		return account;
	}
}
