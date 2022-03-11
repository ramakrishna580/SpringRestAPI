package com.spring.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.spring.entities.Customer;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private TestEntityManager entityManager;
	
	private Customer customer1;
	private Customer customer2;
	
	@BeforeEach
	public void setup() {
		customer1 = new Customer(null, "Mark", "Smith", "mark@email.com", "Boston");
		customer2 = new Customer(null, "John", "White", "john@email.com", "Miami");
		
		entityManager.persist(customer1);
		entityManager.persist(customer2);
	}

	@Test
	@Order(1)
	@DisplayName("Test Find By Id")
	void testFindById() {
		Customer customerFound = repository.findById(1).get();
		
		assertEquals(customer1, customerFound);
	}
	
	@Test
	@Order(2)
	@DisplayName("Test Find All")
	void testFindAll() {
		List<Customer> customerList = repository.findAll();
		
		assertEquals(2, customerList.size());
	}
	
	@Test
	@Order(3)
	@DisplayName("Test Save")
	void testSave() {
		Customer newCustomer = new Customer(null, "May", "Jones", "may@email.com", "Manila");
		Customer createdCustomer = repository.save(newCustomer);
		
		assertEquals(newCustomer, createdCustomer);
		assertEquals(3, repository.findAll().size());
	}
	
	@Test
	@Order(4)
	@DisplayName("Test Delete By Id")
	void testDeleteById() {
		repository.deleteById(8);
		
		assertEquals(1, repository.findAll().size());
	}
	
	@Test
	@Order(5)
	@DisplayName("Test Find By First Name")
	void testFindByFirstName() {
		Customer foundCustomer = repository.findByFirstName("Mark");
		
		assertEquals(customer1, foundCustomer);
	}
	
	@Test
	@Order(6)
	@DisplayName("Test Find By Last Name")
	void testFindByLastName() {
		Customer foundCustomer = repository.findByLastName("White");
		
		assertEquals(customer2, foundCustomer);
	}
}
