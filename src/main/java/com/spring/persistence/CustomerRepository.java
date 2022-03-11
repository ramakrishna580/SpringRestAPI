package com.spring.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
