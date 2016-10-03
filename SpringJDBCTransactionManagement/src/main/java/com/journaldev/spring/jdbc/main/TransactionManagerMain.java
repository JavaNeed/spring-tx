package com.journaldev.spring.jdbc.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.journaldev.spring.jdbc.model.Address;
import com.journaldev.spring.jdbc.model.Customer;
import com.journaldev.spring.jdbc.service.CustomerService;
import com.journaldev.spring.jdbc.service.CustomerServiceImpl;

public class TransactionManagerMain {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");

		CustomerService customerManager = ctx.getBean("customerManager", CustomerServiceImpl.class);

		Customer cust = createDummyCustomer();
		customerManager.createCustomer(cust);

		ctx.close();
	}

	private static Customer createDummyCustomer() {
		Customer customer = new Customer();
		customer.setId(2);
		customer.setName("Pankaj");
		
		Address address = new Address();
		address.setId(2);
		address.setCountry("India");
		
		// setting value more than 20 chars, so that SQLException occurs
		address.setAddress("Albany Dr, San Jose, CA 95129");
		customer.setAddress(address);
		
		return customer;
	}

}
