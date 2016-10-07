package com.journaldev.spring.jdbc.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.jdbc.dao.CustomerDAO;
import com.journaldev.spring.jdbc.model.Customer;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDAO customerDAO;

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	@Override
	//@Transactional(propagation = Propagation.SUPPORTS) - This allows to save the transaction irrespective of issue
	// Ref: http://stackoverflow.com/questions/39922294/spring-transaction-how-to-allow-dependent-transactions-to-be-complted-if-anyon
	@Transactional
	public void createCustomer(Customer cust) {
		customerDAO.create(cust);
	}
}
