package com.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entities.Customer;
import com.spring.entities.Order;
import com.spring.persistence.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerService customerService;
	
	@Override
	public Order findByOrderId(Integer orderId) {
		log.info("inside findByOrderId");
		
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		
		if(optionalOrder.isEmpty()) {
			throw new RuntimeException("Order not found with id = " + orderId);
		}
		
		return optionalOrder.get();
	}

	@Override
	public List<Order> findByOrderByCustomerId(Integer custId) {
		log.info("inside findByOrderByCustomerId");
		
		customerService.findByCustomerId(custId);
		
		return orderRepository.findOrderByCustomerId(custId);
	}

	@Override
	public List<Order> findAllOrders() {
		log.info("inside findAllOrders");
		
		return orderRepository.findAll();
	}

	@Override
	public Order createOrderRecord(Order order) {
		log.info("inside createOrderRecord");
		
		Customer customer = customerService.findByCustomerId(order.getCustomer().getCustId());
		order.getCustomer().setFirstName(customer.getFirstName());
		order.getCustomer().setLastName(customer.getLastName());
		order.getCustomer().setEmail(customer.getEmail());
		order.getCustomer().setLocation(customer.getLocation());
		
		orderRepository.save(order);
		
		return order;
	}

	@Override
	public Order updateOrderRecord(Integer orderId, Order newOrder) {
		log.info("inside updateOrderRecord");
		
		if (!orderRepository.existsById(orderId)) 
			throw new RuntimeException("Order not found with id = " + orderId);

		newOrder.setOrderId(orderId);
		Customer customer = customerService.findByCustomerId(newOrder.getCustomer().getCustId());
		newOrder.getCustomer().setFirstName(customer.getFirstName());
		newOrder.getCustomer().setLastName(customer.getLastName());
		newOrder.getCustomer().setEmail(customer.getEmail());
		newOrder.getCustomer().setLocation(customer.getLocation());
		
		orderRepository.save(newOrder);
		
		return newOrder;
	}

	@Override
	public void deleteOrderRecord(Integer orderId) {
		log.info("inside deleteOrderRecord");
		
		if (!orderRepository.existsById(orderId))
			throw new RuntimeException("Order not found with id = " + orderId);
			
		orderRepository.deleteById(orderId);
	}
}
