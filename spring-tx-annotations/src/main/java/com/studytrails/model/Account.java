package com.studytrails.model;

public class Account {

	private int id;
	private String number;
	private Double balance;

	public Account() {

	}

	public Account(String number, Double initialBalance) {
		super();
		this.number = number;
		this.balance = initialBalance;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double amount) {
		this.balance = amount;
	}

	/**
	 * This method debit the amount 
	 * @param debitAmount
	 */
	public void debit(Double debitAmount) {
		this.balance = this.balance - debitAmount;
	}

	/**
	 * This method credit the amount
	 * @param creditAmount
	 */
	public void credit(Double creditAmount) {
		this.balance = this.balance + creditAmount;
	}

	@Override
	public String toString() {
		return "Account [number=" + number + ", balance=" + balance + "id ="  +id+"]";
	}
}
