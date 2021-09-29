package com.cg.Customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.Customer.dao.ICustomerDao;
import com.cg.Customer.entity.Customer;
import com.cg.Customer.entity.CustomerLogin;
import com.cg.Customer.entity.Loan;
import com.cg.Customer.exception.CustomerAlreadyExistsException;
import com.cg.Customer.exception.CustomerNotFoundException;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService{
	
	@Autowired
	private ICustomerDao dao;
	
	@Override
	public Customer register(Customer customer) {
		boolean exists = customer.getId()!=null && dao.existsById(customer.getId());
		if(exists) {
			throw new CustomerAlreadyExistsException("Customer already exists for id = " + customer.getId());
		}
		customer = dao.save(customer);
		System.out.println("Returning saved value: " + customer);
		return customer;
	}

	@Override
	public List<Customer> findAll() {
		System.out.println(dao.getClass().getName());
		List<Customer> list = dao.findAll();
		return list;
	}

	@Override
	public Customer findById(int id) {
		Optional<Customer> opt = dao.findById(id);
		if(!opt.isPresent()) {
			System.out.println("Error");
			throw new CustomerNotFoundException("Customer Doesn't exist with id : " + id);
		}
		Customer customer = opt.get();
		System.out.println(customer);
		return customer;
	}
	

}
