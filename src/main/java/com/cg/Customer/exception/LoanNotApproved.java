package com.cg.Customer.exception;

public class LoanNotApproved extends RuntimeException{
	public LoanNotApproved(String message) {
		super(message);
	}
}
