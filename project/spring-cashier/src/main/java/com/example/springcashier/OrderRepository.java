package com.example.springcashier;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import com.example.springcashier.model.Order;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface OrderRepository extends CrudRepository<Order, Integer> {

	List<Order> findByOrderNumber(String orderNumber);
}