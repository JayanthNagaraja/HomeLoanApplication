package com.cg.Customer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.Customer.dao.ICustomerLoginDao;
import com.cg.Customer.entity.CustomerLogin;

@Service
@Transactional
public class CustomerLoginServiceImpl implements ICustomerLoginService {
	
	@Autowired
	private ICustomerLoginDao dao;

	@Override
	public CustomerLogin findById(String id) {
		Optional<CustomerLogin> opt = dao.findById(id);
		return opt.get();
	}
	
	

}
