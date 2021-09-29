package com.cg.Customer.service;

import java.util.List;

import com.cg.Customer.entity.Loan;
import com.cg.Customer.entity.LoanTracker;

public interface ICustomerLoanService {

	Loan register(Loan loan);

	Loan findByCustId(int id, int appid);

	List<Loan> findByCustId(int id);

	LoanTracker loanTracker(Loan loan);

	void remove(int applicationid);

}
