package com.hecatesmoon.testingmockitoexercise.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.hecatesmoon.testingmockitoexercise.interfaces.InventoryClient;
import com.hecatesmoon.testingmockitoexercise.interfaces.NotificationSender;
import com.hecatesmoon.testingmockitoexercise.interfaces.OrderRepository;
import com.hecatesmoon.testingmockitoexercise.model.Order;
import com.hecatesmoon.testingmockitoexercise.model.OrderItem;
import com.hecatesmoon.testingmockitoexercise.model.OrderStatus;

public class OrderServiceTest {
    
    private final InventoryClient inventoryClientMock = mock(InventoryClient.class);
    private final NotificationSender notificationSenderMock = mock(NotificationSender.class);
    private final OrderRepository orderRepositoryMock = mock(OrderRepository.class);
    
    private final OrderService orderService = new OrderService(inventoryClientMock, notificationSenderMock, orderRepositoryMock);

    @Test
    public void PlaceOrder_OneItemWithStock(){
        OrderItem item = new OrderItem();
        item.setQuantity(1);
        item.setProductId("shoes");
        item.setPrice(40000);
        ArgumentCaptor<Order> order = ArgumentCaptor.forClass(Order.class);
        
        when(inventoryClientMock.hasStock("shoes", 1)).thenReturn(true);
        orderService.placeOrder("test", List.of(item));
        verify(orderRepositoryMock).save(order.capture());

        Assertions.assertEquals(OrderStatus.CONFIRMED, order.getValue().getStatus());
        Assertions.assertEquals(40000, order.getValue().getTotal());
    }
}
