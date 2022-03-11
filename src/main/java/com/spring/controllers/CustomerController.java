package com.spring.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.CustomerUpdateDTO;
import com.spring.entities.Customer;
import com.spring.service.CustomerService;
import com.spring.service.ValidatorService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ValidatorService validatorService;
	
	@GetMapping("/{custId}")
	public Customer getCustomerById(@PathVariable Integer custId) {
		return customerService.findByCustomerId(custId);
	}
	
	@GetMapping("/all")
	public List<Customer> getAllCustomers() {
		return customerService.findAllCustomers();
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
		Map<String, String> map = validatorService.validate(bindingResult);
		
		if(map.isEmpty()) {
			Customer createdCustomer = customerService.createCustomerRecord(customer);
			return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{custId}")
	public ResponseEntity<Object> updateCustomer(@PathVariable Integer custId, @Valid @RequestBody Customer customer,
			BindingResult bindingResult) {
		Map<String, String> map = validatorService.validate(bindingResult);
		
		if(map.isEmpty()) {
			Customer updatedCustomer = customerService.updateCustomerRecord(custId, customer);
			return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{custId}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Integer custId) {
		customerService.deleteCustomerRecord(custId);
		return new ResponseEntity<>("Customer deleted successsfully", HttpStatus.OK);
	}
	
	@PatchMapping("/patch/{custId}")
	public ResponseEntity<Object> updateCustomerPartially(@PathVariable Integer custId, 
			@Valid @RequestBody CustomerUpdateDTO customerUpdateDto,
			BindingResult bindingResult) {
		Map<String, String> map = validatorService.validate(bindingResult);
		
		if(map.isEmpty()) {
			Customer customer = customerService.updateCustomerRecordPartially(custId, customerUpdateDto);
			return new ResponseEntity<>(customer, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
	}
}
