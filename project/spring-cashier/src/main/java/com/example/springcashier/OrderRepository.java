package com.example.springcashier;

import org.springframework.data.repository.CrudRepository;

import com.example.springcashier.Order;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface OrderRepository extends CrudRepository<Order, Integer> {

}