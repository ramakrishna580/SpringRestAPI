package com.spring.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dto.CustomerUpdateDTO;
import com.spring.entities.Customer;
import com.spring.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerService customerService;
	
	private Customer customer;
	private Integer custId = 1;
	
	@BeforeEach
	public void setup() {
		customer = new Customer();
		customer.setCustId(custId);
		customer.setFirstName("Mark");
		customer.setLastName("Smith");
		customer.setEmail("mark@email.com");
		customer.setLocation("Los Angeles");
	}
	
	@AfterEach
	public void tearDown() {
		customer = null;
	}
	
	@Test
	@DisplayName("Test Get Customer By Id")
	void testGetCustomerById() throws Exception {
		when(customerService.findByCustomerId(custId)).thenReturn(customer);
		
		mockMvc.perform(get("/customers/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.custId").value(customer.getCustId()))
				.andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(customer.getLastName()))
				.andExpect(jsonPath("$.email").value(customer.getEmail()))
				.andExpect(jsonPath("$.location").value(customer.getLocation()));
	}

	@Test
	@DisplayName("Test Get All Customers")
	void testGetAllCustomers() throws Exception {
		Customer customer2 = new Customer(2, "Mich", "White", "mich@email.com", "Miami");
		
		List<Customer> customerList = new ArrayList<>();
		customerList.add(customer);
		customerList.add(customer2);
		
		when(customerService.findAllCustomers()).thenReturn(customerList);
		
		mockMvc.perform(get("/customers/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(2)));
	}

	@Test
	@DisplayName("Test Create Customer")
	void testCreateCustomer() throws Exception {
		when(customerService.createCustomerRecord(customer)).thenReturn(customer);
		
		mockMvc.perform(post("/customers/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customer)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.custId").exists());
	}
	
	@Test
	@DisplayName("Test Create Customer Null First Name")
	void testCreateCustomerNullFirstName() throws Exception {
		customer = new Customer(1, null, "Cruz", "cruz@email.com", "Manila");
		
		mockMvc.perform(post("/customers/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customer)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.firstName", is("First Name cannot be null or blank")));
	}
	
	@Test
	@DisplayName("Test Create Customer Null Last Name")
	void testCreateCustomerNullLastName() throws Exception {
		customer = new Customer(1, "Mike", null, "mike@email.com", "Manila");
		
		mockMvc.perform(post("/customers/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customer)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.lastName", is("Last Name cannot be null or blank")));
	}

	@Test
	@DisplayName("Test Create Customer Blank Email Name")
	void testCreateCustomerBlankEmail() throws Exception {
		customer = new Customer(1, "Mike", "Cruz", "", "Manila");
		
		mockMvc.perform(post("/customers/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customer)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email", is("Email cannot be null or blank")));
	}

	@Test
	@DisplayName("Test Create Customer Blank Location")
	void testCreateCustomerBlankLocation() throws Exception {
		customer = new Customer(1, "Mike", "Cruz", "mike@email.com", "");
		
		mockMvc.perform(post("/customers/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customer)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.location", is("Location cannot be null or blank")));
	}
	
	@Test
	@DisplayName("Test Update Customer")
	void testUpdateCustomer() throws Exception {
		Customer updatedCustomer = new Customer(custId, "Markk", "Smithh", "markk@email.com", "LA");
		
		when(customerService.updateCustomerRecord(custId, updatedCustomer))
				.thenReturn(updatedCustomer);

		mockMvc.perform(put("/customers/update/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updatedCustomer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(updatedCustomer.getFirstName()));
	}
	
	@Test
	@DisplayName("Test Update Customer Null First Name")
	void testUpdateCustomerNullFirstName() throws Exception {
		Customer updatedCustomer = new Customer(1, null, "Smithh", "markk@email.com", "LA");
		
		when(customerService.updateCustomerRecord(custId, updatedCustomer))
				.thenReturn(updatedCustomer);

		mockMvc.perform(put("/customers/update/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updatedCustomer)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.firstName", is("First Name cannot be null or blank")));
	}
	
	@Test
	@DisplayName("Test Delete Customer")
	void testDeleteCustomer() throws Exception {
		mockMvc.perform(delete("/customers/delete/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Customer deleted successsfully"));
		
		verify(customerService, times(1)).deleteCustomerRecord(custId);
	}

	@Test
	@DisplayName("Test Update CustomerPartially")
	void testUpdateCustomerPartially() throws Exception {
		CustomerUpdateDTO newCustomer = new CustomerUpdateDTO();
		newCustomer.setFirstName("Test");
		customer.setFirstName(newCustomer.getFirstName());
		
		when(customerService.updateCustomerRecordPartially(custId, newCustomer))
				.thenReturn(customer);

		mockMvc.perform(patch("/customers/patch/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(newCustomer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(newCustomer.getFirstName()));
	}
	
	@Test
	@DisplayName("Test Update Customer Partially Invalid Email Format")
	void testUpdateCustomerPartiallyInvalidEmailFormat() throws Exception {
		CustomerUpdateDTO newCustomer = new CustomerUpdateDTO();
		newCustomer.setEmail("test.com");
		customer.setEmail(newCustomer.getEmail());
		
		when(customerService.updateCustomerRecordPartially(custId, newCustomer))
				.thenReturn(customer);

		mockMvc.perform(patch("/customers/patch/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(customer)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.email", is("Email should be valid email format")));
	}
}
