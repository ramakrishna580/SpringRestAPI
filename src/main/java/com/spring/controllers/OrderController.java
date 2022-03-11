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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entities.Order;
import com.spring.service.OrderService;
import com.spring.service.ValidatorService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ValidatorService validatorService;
	
	@GetMapping("/{orderId}")
	public Order getOrderById(@PathVariable Integer orderId) {
		return orderService.findByOrderId(orderId);
	}
	
	@GetMapping("/customer/{custId}")
	public List<Order> getOrderByCustomerId(@PathVariable Integer custId) {
		List<Order> orderList = orderService.findByOrderByCustomerId(custId);
		
		if (orderList.isEmpty()) 
			throw new RuntimeException("No orders found on customer id : " + custId);
		else 
			return orderList;
	}
	
	@GetMapping("/all")
	public List<Order> getAllOrders() {
		return orderService.findAllOrders();
	}
	
	@PostMapping("/create")
	public ResponseEntity<Object> createOrder(@Valid @RequestBody Order order, BindingResult bindingResult) {
		Map<String, String> map = validatorService.validate(bindingResult);
		
		if(map.isEmpty()) {
			Order createdOrder = orderService.createOrderRecord(order);
			return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{orderId}")
	public ResponseEntity<Object> updateOrder(@PathVariable Integer orderId, @Valid @RequestBody Order order,
			BindingResult bindingResult) {
		Map<String, String> map = validatorService.validate(bindingResult);
		
		if(map.isEmpty()) {
			Order updatedOrder = orderService.updateOrderRecord(orderId, order);
			return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{orderId}")
	public ResponseEntity<Object> deleteOrder(@PathVariable Integer orderId) {
		orderService.deleteOrderRecord(orderId);
		return new ResponseEntity<>("Order deleted successsfully", HttpStatus.OK);
	}
}
