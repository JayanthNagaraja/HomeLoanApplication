package com.cg.Customer.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.Customer.entity.Customer;
import com.cg.Customer.entity.Loan;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Integer> {

	Loan save(Loan loan);
	
}
