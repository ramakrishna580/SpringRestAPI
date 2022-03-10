package com.spring.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dto.CustomerUpdateDTO;
import com.spring.entities.Customer;
import com.spring.persistence.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public Customer findByCustomerId(Integer custId) {
		log.info("inside findByCustomerId");
		
		Optional<Customer> optionalCustomer = customerRepository.findById(custId);
		if(optionalCustomer.isEmpty()) {
			throw new RuntimeException("Customer not found with id = " + custId);
		}
		
		return optionalCustomer.get();
	}

	@Override
	public List<Customer> findAllCustomers() {
		log.info("inside findAllCustomers");
		
		return customerRepository.findAll();
	}

	@Override
	public Customer createCustomerRecord(Customer customer) {
		log.info("inside createCustomerRecord");
		
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomerRecord(Integer custId, Customer newCustomer) {
		log.info("inside updateCustomerRecord");
		
		if (!customerRepository.existsById(custId)) 
			throw new RuntimeException("Customer not found with id = " + custId);
		
		newCustomer.setCustId(custId);
		return customerRepository.save(newCustomer);
	}

	@Override
	public void deleteCustomerRecord(Integer custId) {
		log.info("inside deleteCustomerRecord");
		
		if (!customerRepository.existsById(custId))
			throw new RuntimeException("Customer not found with id = " + custId);
		
		customerRepository.deleteById(custId);
	}
	
	@Override
	public Customer updateCustomerRecordPartially(Integer custId, CustomerUpdateDTO customerUpdateDto) {
		log.info("inside updateCustomerRecordPartially");

		Customer customer = findByCustomerId(custId);
		
		if (Objects.nonNull(customerUpdateDto.getFirstName())) 
			customer.setFirstName(customerUpdateDto.getFirstName());
     
        if (Objects.nonNull(customerUpdateDto.getLastName())) 
        	customer.setLastName(customerUpdateDto.getLastName());
        
        if (Objects.nonNull(customerUpdateDto.getEmail())) 
        	customer.setEmail(customerUpdateDto.getEmail());
        
        if (Objects.nonNull(customerUpdateDto.getLocation())) 
        	customer.setLocation(customerUpdateDto.getLocation());
        
		return customerRepository.save(customer);
	}
}
