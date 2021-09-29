package com.cg.Customer.exception;

public class CustomerAlreadyExistsException extends RuntimeException {
	
	public CustomerAlreadyExistsException(String msg) {
		super(msg);
	}

}
