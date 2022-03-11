package com.spring.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	public Customer findByFirstName(String firstName);
	
	public Customer findByLastName(String lastName);
	
}
