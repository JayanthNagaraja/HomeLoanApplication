package com.cg.Customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CentralizedExceptionHandler {
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CustomerAlreadyExistsException.class)
	public String handleCustomerExists(CustomerAlreadyExistsException e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CustomerNotFoundException.class)
	public String handleCustomerDoesntExist(CustomerNotFoundException e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CustomerApplicationNotFoundException.class)
	public String handleCustomerDoesntExist(CustomerApplicationNotFoundException e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CustomerLandOrFinanceException.class)
	public String handleCustomerLandOrFinanceVerification(CustomerLandOrFinanceException e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ExceptionHandler(CustomerApprovedException.class)
	public String handleCustomerApproved(CustomerApprovedException e) {
		return e.getMessage();
	}
	
}
