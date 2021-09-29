package com.cg.Customer.service;

import com.cg.Customer.entity.CustomerLogin;

public interface ICustomerLoginService {

	CustomerLogin findById(String id);

}
