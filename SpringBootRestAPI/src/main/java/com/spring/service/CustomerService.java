package com.spring.service;

import java.util.List;

import com.spring.dto.CustomerUpdateDTO;
import com.spring.entities.Customer;

public interface CustomerService {
	public Customer findByCustomerId(Integer custId);
	
	public List<Customer> findAllCustomers();
	
	public Customer createCustomerRecord(Customer customer);

	public Customer updateCustomerRecord(Integer custId, Customer newCustomer);
	
	public void deleteCustomerRecord(Integer custId);
	
	public Customer updateCustomerRecordPartially(Integer custId, CustomerUpdateDTO customerUpdateDto);
}
