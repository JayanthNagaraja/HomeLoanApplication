package com.cg.Customer.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cg.Customer.dto.CreateCustomerRequest;
import com.cg.Customer.dto.CreateLoanRequest;
import com.cg.Customer.dto.CustomerDetails;
import com.cg.Customer.dto.LoanDetails;
import com.cg.Customer.entity.Customer;
import com.cg.Customer.entity.CustomerLogin;
import com.cg.Customer.entity.Emi;
import com.cg.Customer.entity.Loan;
import com.cg.Customer.entity.LoanTracker;
import com.cg.Customer.exception.CustomerApprovedException;
import com.cg.Customer.exception.CustomerLandOrFinanceException;
import com.cg.Customer.exception.LoanNotApproved;
import com.cg.Customer.service.ICustomerLoanService;
import com.cg.Customer.service.ICustomerLoginService;
import com.cg.Customer.service.ICustomerService;
import com.cg.Customer.util.CustomerUtil;
import com.cg.Customer.util.LoanUtil;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {
	private Logger logger = Logger.getLogger(CustomerController.class);
	
	@Autowired
	private ICustomerLoginService customerLoginService;
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private ICustomerLoanService customerLoanService;
	
	@Autowired
	private CustomerUtil customerUtil;
	
	@Autowired
	private LoanUtil loanUtil;
	
//	@GetMapping("/by/show")
//	public String showData() {
//		return "Hello";
//	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/add")
	public CustomerDetails add(@RequestBody @Valid CreateCustomerRequest requestData) {
		System.out.println("Req data: " + requestData);
		Customer customer = new Customer(requestData.getName(), requestData.getMobileNumber(), requestData.getEmailId(), requestData.getDate(), 
				requestData.getGender(), requestData.getNationality(), requestData.getAadharNumber(), requestData.getPan());
		System.out.println("Customer came: " + customer);
		customer = customerService.register(customer);
		CustomerDetails details = customerUtil.toDetails(customer);
		logger.info("Customer Details added: "+details);
		return details;
	}
	
	@GetMapping("/check/{userid}/{password}")
	public CustomerLogin login(@PathVariable("userid") String id, @PathVariable("password") String password) {
		System.out.println("Controller fetch Customer with id : " + id);
		CustomerLogin loginCreds = customerLoginService.findById(id);
		logger.info("Customer Logged in: "+loginCreds);
		return loginCreds;
	}
	
	@GetMapping("/allcustomers")
	public List<CustomerDetails> findall(){
		List<Customer> customers = customerService.findAll();
//		System.out.println(customers);
		List<CustomerDetails> details = customerUtil.toDetails(customers);
		return details;
	}
	
	@GetMapping("/by/id/{id}")
	public  CustomerDetails findCustomerById(@PathVariable("id") int id) {
		System.out.println("Controller fetch id: " + id);
		Customer customer  = customerService.findById(id);
		CustomerDetails details = customerUtil.toDetails(customer);
		return details;
	}
	
	@GetMapping("/loan/Apply/{date}/{applyamount}/{custid}")
	public LoanDetails addLoan(@PathVariable("date") Date date, @PathVariable("applyamount") float amount, @PathVariable("custid") int id) {
		System.out.println("Controller fetch id: " + id);
		Customer customer = customerService.findById(id);
		Loan loan = new Loan(date, amount, id, "Not Approved");
		loan = customerLoanService.register(loan);
		LoanDetails details = loanUtil.toDetails(loan);
		logger.info("New Loan added: "+details);
		return details;
	}
	
	@GetMapping("/loan/updateFinanceDocuments/{customerid}/{applicationid}/{finance}")
	public LoanDetails updateLoanFinance(@PathVariable("customerid") int id,@PathVariable("applicationid") int appid, @PathVariable("finance") boolean financeupdate) {
		System.out.println("Controller fetch Customer id: " + id + " with Application id:" + appid);
		Customer customer = customerService.findById(id);
		Loan loan = customerLoanService.findByCustId(id, appid);
		if(loan.getStatus().equals("Approved") || loan.getStatus().equals("Rejected")) {
			throw new CustomerApprovedException("Already Updated....... Can't update again");
		}else {
			loan.setFinanceverify(financeupdate);
			loan = customerLoanService.register(loan);
			logger.info("Finance documents updated for custId: "+id+" with applicationId: "+appid);
		}
		LoanDetails details = loanUtil.toDetails(loan);
		return details;
	}
	
	@GetMapping("/loan/updateLandDocuments/{customerid}/{applicationid}/{landdocuments}")
	public LoanDetails updateLoanLandDocuments(@PathVariable("customerid") int id,@PathVariable("applicationid") int appid, @PathVariable("landdocuments") boolean landdocumentsupdate) {
		System.out.println("Controller fetch Customer id: " + id + " with Application id:" + appid);
		Customer customer = customerService.findById(id);
		Loan loan = customerLoanService.findByCustId(id, appid);
		if(loan.getStatus().equals("Approved") || loan.getStatus().equals("Rejected")) {
			throw new CustomerApprovedException("Already Updated....... Can't update again");
		}else {
			loan.setLandverify(landdocumentsupdate);
			loan = customerLoanService.register(loan);
			logger.info("Land documents updated for custId: "+id+" with applicationId: "+appid);
		}
		LoanDetails details = loanUtil.toDetails(loan);
		return details;
	}
	
	@GetMapping("/loan/updateAdminApprove/{customerid}/{applicationid}/{approvedamount}/{adminapprove}/{rejectdata}")
	public LoanDetails updateAdminApproval(@PathVariable("customerid") int id, @PathVariable("applicationid") int appid, 
			@PathVariable("approvedamount") float approveamount, @PathVariable("adminapprove") boolean adminapprovalupdate,
			@PathVariable("rejectdata") String reject) {
		System.out.println("Controller fetch Customer id: " + id + " with Application id:" + appid);
		Customer customer = customerService.findById(id);
		Loan loan = customerLoanService.findByCustId(id, appid);
		if(!loan.isLandverify() || !loan.isFinanceverify()) {
			throw new CustomerLandOrFinanceException("Land Documents or Finance Document Approval Required");
		}else if(loan.getStatus().equals("Approved") || loan.getStatus().equals("Rejected")) {
			throw new CustomerApprovedException("Already Updated....... Can't update again");
		}else {
			if(reject.equals("r")) {
				loan.setAdminapprove(false);
				loan.setApproveamount(approveamount);
				loan.setStatus("Rejected");
				loan = customerLoanService.register(loan);
				logger.info("Loan rejected for custId: "+id+" with applicationId: "+appid);
			}else {
				loan.setAdminapprove(adminapprovalupdate);
				loan.setApproveamount(approveamount);
				loan.setStatus("Approved");
				loan = customerLoanService.register(loan);
				logger.info("Loan approved for custId: "+id+" with applicationId: "+appid);
			}
			
			
		}
		LoanDetails details = loanUtil.toDetails(loan);
		return details;
	}
	
	@GetMapping("/loan/getAllLoansAppliedByCustomerId/{id}")
	public List<LoanDetails> getAllLoansByCustomerId(@PathVariable("id") int id){
		System.out.println("Controller fetch Loan Applications of Customer with id: " + id);
		Customer customer = customerService.findById(id);
		List<Loan> loans = customerLoanService.findByCustId(id);
		List<LoanDetails> details = loanUtil.toDetails(loans);
		return details;
	}
	
	@GetMapping("/loan/loanTracker/{custid}/{applicationid}")
	public LoanTracker getLoanDetails(@PathVariable("custid") int id, @PathVariable("applicationid") int appid) {
		Customer customer = customerService.findById(id);
		Loan loan = customerLoanService.findByCustId(id, appid);
		LoanTracker lt = new LoanTracker();
		
		lt = customerLoanService.loanTracker(loan);
		
		return lt;
	}
	
	@GetMapping("/approvedEMI/{custid}/{applicationid}/{rateOfInterest}/{timePeriod}")
	public Emi calculateApprovedEmi(@PathVariable("custid") int id, @PathVariable("applicationid") int appid,@PathVariable("rateOfInterest") double rateOfInterest, @PathVariable("timePeriod") int timePeriod) {
		Customer customer = customerService.findById(id);
		Loan loan = customerLoanService.findByCustId(id, appid);
		Emi emi = null;
		if(loan.getStatus().equals("Approved")){
			double loanAmount = loan.getApproveamount();
			double interest = (double) ((loanAmount * (rateOfInterest * 0.01))/timePeriod);
			double emiAmount = ((loanAmount/timePeriod) + interest);
			double totalEmiAmount = interest * timePeriod;
			double totalAmount = emiAmount * timePeriod;
			emi = new Emi(loanAmount, rateOfInterest, timePeriod, interest, emiAmount, totalEmiAmount, totalAmount);
			return emi;
		}
		else {
			throw new LoanNotApproved("Loan is not approved to check EMI");
		}
	}
	
	@GetMapping("/simpleEMI/{loanAmount}/{rateOfInterest}/{timePeriod}")
	public Emi calculateEmi(@PathVariable("loanAmount") float loanAmount,@PathVariable("rateOfInterest") double rateOfInterest, @PathVariable("timePeriod") int timePeriod) {
		double interest = (double) ((loanAmount * (rateOfInterest * 0.01))/timePeriod);
		double emiAmount = ((loanAmount/timePeriod) + interest);
		double totalEmiAmount = interest * timePeriod;
		double totalAmount = emiAmount * timePeriod;
		Emi emi = new Emi(loanAmount, rateOfInterest, timePeriod, interest, emiAmount, totalEmiAmount, totalAmount);
		return emi;
	}
	
	@GetMapping("/loan/removeLoanByCustomerId/{id}/{appid}")
	public LoanDetails deleteLoan(@PathVariable("id") int id, @PathVariable("appid") int appid) {
		logger.info("Deleting loan for customer id : " + id + " with application id : " + appid);
		Customer customer = customerService.findById(id);
		Loan loan = customerLoanService.findByCustId(id, appid);
		if(loan.getStatus().equals("Approved") || loan.getStatus().equals("Rejected")) {
			logger.error("Tried to delete approved/rejected loan.......");
			throw new CustomerApprovedException("Cannot delete approved/rejected loans");
		}
		LoanDetails details = loanUtil.toDetails(loan);
		customerLoanService.remove(loan.getApplicationid());
		return details;
	}
	
}
