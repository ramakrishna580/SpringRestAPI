package com.spring.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query("select o from order_table o where cust_id = :custId")
    public List<Order> findOrderByCustomerId(@Param("custId") Integer custId);
}
