package com.hecatesmoon.testingmockitoexercise.interfaces;

import java.util.List;
import java.util.Optional;

import com.hecatesmoon.testingmockitoexercise.model.Order;


public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(String orderId);
    List<Order> findByCustomerId(String CustomerId);
}
