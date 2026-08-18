package com.hecatesmoon.testingmockitoexercise.interfaces;

import java.util.Optional;

import com.hecatesmoon.testingmockitoexercise.model.Order;


public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(String orderId);
}
