package com.journaldev.spring.jdbc.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.jdbc.model.Address;
import com.journaldev.spring.jdbc.model.Customer;
import com.journaldev.spring.jdbc.service.CustomerService;
import com.journaldev.spring.jdbc.service.CustomerServiceImpl;

public class TransactionManagerMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");

		CustomerService customerService = ctx.getBean("customerService", CustomerServiceImpl.class);

		Customer cust = createDummyCustomer();
		customerService.createCustomer(cust);

		ctx.close();
	}

	private static Customer createDummyCustomer() {
		// create Customer
		Customer customer = new Customer();
		customer.setId(2);
		customer.setName("Pankaj");
		
		// create Address
		Address address = new Address();
		address.setId(2);
		address.setCountry("India");
		
		// setting value more than 20 chars, so that SQLException occurs
		address.setAddress("Albany Dr, San Jose, CA 95129");
		customer.setAddress(address);
		
		return customer;
	}
}
