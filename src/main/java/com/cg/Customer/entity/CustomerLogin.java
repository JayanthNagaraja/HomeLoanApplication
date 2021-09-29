package com.cg.Customer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "logincreds")
public class CustomerLogin {
	
	@Id
	private String userid;
	private String password;
	private String type;
	@OneToOne
	@JoinColumn(name = "cust_id")
	private Customer customer;
	
	public CustomerLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerLogin(String userid, String password, String type) {
		super();
		this.userid = userid;
		this.password = password;
		this.type = type;
	}
	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CustomerLogin [userid=" + userid + ", password=" + password + ", type=" + type + ", customer="
				+ customer + "]";
	}

}
