package com.cg.Customer.service;

import java.util.List;

import com.cg.Customer.entity.Customer;
import com.cg.Customer.entity.CustomerLogin;
import com.cg.Customer.entity.Loan;

public interface ICustomerService {

	Customer register(Customer customerList);

	List<Customer> findAll();

	Customer findById(int id);

}
