package com.cg.Customer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.Customer.entity.CustomerLogin;

@Repository
public interface ICustomerLoginDao extends JpaRepository<CustomerLogin, String> {
	
	
	
}
