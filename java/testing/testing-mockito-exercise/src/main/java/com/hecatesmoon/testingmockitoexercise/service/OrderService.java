package com.hecatesmoon.testingmockitoexercise.service;

import java.util.List;
import java.util.UUID;

import com.hecatesmoon.testingmockitoexercise.exceptions.InsufficientStockException;
import com.hecatesmoon.testingmockitoexercise.exceptions.OrderAlreadyCancelledException;
import com.hecatesmoon.testingmockitoexercise.exceptions.OrderNotFoundException;
import com.hecatesmoon.testingmockitoexercise.interfaces.*;
import com.hecatesmoon.testingmockitoexercise.model.Order;
import com.hecatesmoon.testingmockitoexercise.model.OrderItem;
import com.hecatesmoon.testingmockitoexercise.model.OrderStatus;

public class OrderService {
    
    private final InventoryClient inventoryClient;
    private final NotificationSender notificationSender;
    private final OrderRepository orderRepository;

    public OrderService (InventoryClient inventoryClient,
                         NotificationSender notificationSender,
                         OrderRepository orderRepository){
        this.inventoryClient = inventoryClient;
        this.notificationSender = notificationSender;
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(String customerId, List<OrderItem> items){
        if (items == null) throw new IllegalArgumentException("The items list is null.");
        if (items.isEmpty()) throw new IllegalArgumentException("The items list is empty.");

        for (OrderItem item : items) {
            if (!inventoryClient.hasStock(item.getProductId(), item.getQuantity())){
                throw new InsufficientStockException("We do not have enough stock for this item: " + item.getProductId());
            }
        }

        for (OrderItem item : items) {
            inventoryClient.reserveStock(item.getProductId(), item.getQuantity());
        }

        double total = 0;

        for (OrderItem item : items){
            total += item.getQuantity() * item.getPrice();
        }

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setItems(items);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotal(total);
        order.setId(UUID.randomUUID().toString());

        order = orderRepository.save(order);

        notificationSender.sendOrderConfirmation(customerId, order.getId());

        return order;
    }

    public void cancelOrder(String orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("This order could not be found: " + orderId));
        if (order.getStatus() == OrderStatus.CANCELLED) throw new OrderAlreadyCancelledException("This order was already cancelled: " + orderId);

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        notificationSender.sendCancellationNotice(order.getCustomerId(), order.getId());
    }

    public Order getOrder(String orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("This order could not be found: " + orderId));
    }

    public List<Order> getOrdersByCustomer(String customerId){
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        List<Order> confirmedOrders = orders.stream().filter(o -> o.getStatus() == OrderStatus.CONFIRMED).toList();

        return confirmedOrders;
    }
}
