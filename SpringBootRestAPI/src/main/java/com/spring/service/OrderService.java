package com.spring.service;

import java.util.List;

import com.spring.entities.Order;

public interface OrderService {
	public Order findByOrderId(Integer orderId);
	
	public List<Order> findByOrderByCustomerId(Integer custId);
	
	public List<Order> findAllOrders();
	
	public Order createOrderRecord(Order order);

	public Order updateOrderRecord(Integer orderId, Order newOrder);
	
	public void deleteOrderRecord(Integer orderId);
}
