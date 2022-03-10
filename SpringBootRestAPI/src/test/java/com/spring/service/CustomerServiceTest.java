package com.spring.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.dto.CustomerUpdateDTO;
import com.spring.entities.Customer;
import com.spring.persistence.CustomerRepository;

@SpringBootTest
class CustomerServiceTest {

	@Mock
	private CustomerRepository repository;
	
	@InjectMocks
	private CustomerService service = new CustomerServiceImpl();
	
	private Customer customer;
	private Integer custId = 1;

	@BeforeEach
	public void setup() {
		customer = new Customer();
		customer.setCustId(1);
		customer.setFirstName("Mark");
		customer.setLastName("Smith");
		customer.setEmail("mark@email.com");
		customer.setLocation("Miami");
	}
	
	@AfterEach
	public void tearDown() {
		customer = null;
	}
	
	@Test
	@DisplayName("Test Find By Customer Id")
	void testFindByCustomerId() {
		Optional<Customer> optionalCustomer = Optional.of(customer);
		
		when(repository.findById(custId)).thenReturn(optionalCustomer);
		
		assertEquals(customer, service.findByCustomerId(custId));
	}

	@Test
	@DisplayName("Test Find By Customer Id Not Exists")
	void testFindByCustomerIdNotExists() {
		Optional<Customer> optionalCustomer = Optional.empty();
		
		when(repository.findById(custId)).thenReturn(optionalCustomer);

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> service.findByCustomerId(custId));
		
		assertEquals("Customer not found with id = " + custId, exception.getMessage());
	}
	
	@Test
	@DisplayName("Test Find All Customers")
	void testFindAllCustomers() {
		List<Customer> customerList = new ArrayList<>();
		customerList.add(customer);
		
		when(repository.findAll()).thenReturn(customerList);

		assertEquals(1, service.findAllCustomers().size());
	}
	
	@Test
	@DisplayName("Test Create Customer Record")
	void testCreateCustomerRecord() {
		when(repository.save(customer)).thenReturn(customer);
		
		assertEquals(customer, service.createCustomerRecord(customer));
	}
	
	@Test
	@DisplayName("Test Update Customer Record")
	void testUpdateCustomerRecord() {
		when(repository.existsById(custId)).thenReturn(true);
		when(repository.save(customer)).thenReturn(customer);
		
		assertEquals(customer, service.updateCustomerRecord(1, customer));
	}

	@Test
	@DisplayName("Test Update Customer Record When Customer Not Exists")
	void testUpdateCustomerRecordCustomerNotExists() {
		when(repository.existsById(custId)).thenReturn(false);
		
		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> service.updateCustomerRecord(custId, customer));
		
		assertEquals("Customer not found with id = " + custId, exception.getMessage());
	}
	
	@Test
	@DisplayName("Test Delete Customer Record")
	void testDeleteCustomerRecord() {
		when(repository.existsById(custId)).thenReturn(true);
		
		doNothing().when(repository).deleteById(custId);
		service.deleteCustomerRecord(custId);
		
		verify(repository, times(1)).deleteById(custId);
	}
	
	@Test
	@DisplayName("Test Delete Customer Record When Customer Not Exists")
	void testDeleteCustomerRecordCustomerNotExists() {
		when(repository.existsById(custId)).thenReturn(false);
		
		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> service.deleteCustomerRecord(custId));
		
		assertEquals("Customer not found with id = " + custId, exception.getMessage());
	}
	
	@Test
	@DisplayName("Test Update Customer Record Partially")
	void testUpdateCustomerRecordPartially() {
		CustomerUpdateDTO newCustomer = new CustomerUpdateDTO("Markk", "Smithh", null, null);
		
		customer.setFirstName(newCustomer.getFirstName());
		customer.setLastName(newCustomer.getLastName());
		
		Optional<Customer> optionalCustomer = Optional.of(customer);
		
		when(repository.findById(custId)).thenReturn(optionalCustomer);
		when(repository.save(customer)).thenReturn(customer);
		
		Customer updatedCustomer = service.updateCustomerRecordPartially(1, newCustomer);
		
		assertEquals(customer.getFirstName(), updatedCustomer.getFirstName());
		assertEquals(customer.getLastName(), updatedCustomer.getLastName());
	}
	
	@Test
	@DisplayName("Test Update Customer Record Partially When Customer Id Not Exists")
	void testUpdateCustomerRecordPartiallyCustomerIdNotExists() {
		CustomerUpdateDTO newCustomer = new CustomerUpdateDTO("Bill", null, null, null);
		
		Optional<Customer> optionalCustomer = Optional.empty();
		
		when(repository.findById(custId)).thenReturn(optionalCustomer);

		RuntimeException exception = assertThrows(RuntimeException.class,
				() -> service.updateCustomerRecordPartially(custId, newCustomer));
		
		assertEquals("Customer not found with id = " + custId, exception.getMessage());
	}
}
