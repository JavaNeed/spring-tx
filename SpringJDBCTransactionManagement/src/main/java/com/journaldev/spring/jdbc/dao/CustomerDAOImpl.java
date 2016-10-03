package com.journaldev.spring.jdbc.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.journaldev.spring.jdbc.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(Customer customer) {
		String queryCustomer = "insert into Customer (id, name) values (?,?)";
		String queryAddress = "insert into Address (id, address,country) values (?,?,?)";

		jdbcTemplate.update(queryCustomer, new Object[] { customer.getId(), customer.getName() });
		System.out.println("Inserted into Customer Table Successfully");

		jdbcTemplate.update(queryAddress, new Object[] { customer.getId(), customer.getAddress().getAddress(),customer.getAddress().getCountry() });
		System.out.println("Inserted into Address Table Successfully");
	}

}
