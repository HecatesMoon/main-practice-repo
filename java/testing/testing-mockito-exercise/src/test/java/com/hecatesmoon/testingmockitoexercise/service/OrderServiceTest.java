package com.hecatesmoon.testingmockitoexercise.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.hecatesmoon.testingmockitoexercise.exceptions.InsufficientStockException;
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
    public void placeOrder_OneItemWithStock(){
        OrderItem item = new OrderItem();
        item.setQuantity(1);
        item.setProductId("shoes");
        item.setPrice(40000);
        ArgumentCaptor<Order> order = ArgumentCaptor.forClass(Order.class);
        
        when(inventoryClientMock.hasStock("shoes", 1)).thenReturn(true);
        orderService.placeOrder("test", List.of(item));
        verify(inventoryClientMock, times(1)).reserveStock(item.getProductId(), item.getQuantity());
        verify(orderRepositoryMock, times(1)).save(order.capture());
        verify(notificationSenderMock, times(1)).sendOrderConfirmation("test", order.getValue().getId());

        Assertions.assertEquals(OrderStatus.CONFIRMED, order.getValue().getStatus());
        Assertions.assertEquals(40000, order.getValue().getTotal());
    }

    @Test
    public void placeOrder_OneItemWithoutStock(){
        OrderItem item = new OrderItem();
        item.setQuantity(1);
        item.setProductId("shoes");
        item.setPrice(40000);
        
        when(inventoryClientMock.hasStock("shoes", 1)).thenReturn(false);

        Assertions.assertThrows(InsufficientStockException.class, () -> {
            orderService.placeOrder("test", List.of(item));
        });

        verify(inventoryClientMock, never()).reserveStock(anyString(), anyInt());
        verifyNoInteractions(notificationSenderMock);
        verifyNoInteractions(orderRepositoryMock);
    }

    @Test
    public void placeOrder_MoreThanOneItemWithStocks(){
        OrderItem item1 = new OrderItem();
        item1.setProductId("shirt");
        item1.setQuantity(2);
        item1.setPrice(16000);
        OrderItem item2 = new OrderItem();
        item2.setProductId("cap");
        item2.setQuantity(1);
        item2.setPrice(12000);
        OrderItem item3 = new OrderItem();
        item3.setProductId("trousers");
        item3.setQuantity(3);
        item3.setPrice(20000);
        List<OrderItem> itemsList = List.of(item1, item2, item3);
        String customerId = "test";
        ArgumentCaptor<Order> order = ArgumentCaptor.forClass(Order.class);

        when(inventoryClientMock.hasStock(item1.getProductId(), item1.getQuantity())).thenReturn(true);
        when(inventoryClientMock.hasStock(item2.getProductId(), item2.getQuantity())).thenReturn(true);
        when(inventoryClientMock.hasStock(item3.getProductId(), item3.getQuantity())).thenReturn(true);

        orderService.placeOrder(customerId, itemsList);

        verify(inventoryClientMock, times(1)).reserveStock(item1.getProductId(), item1.getQuantity());
        verify(inventoryClientMock, times(1)).reserveStock(item2.getProductId(), item2.getQuantity());
        verify(inventoryClientMock, times(1)).reserveStock(item3.getProductId(), item3.getQuantity());
        verify(orderRepositoryMock, times(1)).save(order.capture());
        verify(notificationSenderMock, times(1)).sendOrderConfirmation(customerId, order.getValue().getId());


        Assertions.assertEquals(order.getValue().getTotal(), 104000);
        Assertions.assertEquals(order.getValue().getStatus(), OrderStatus.CONFIRMED);
    }

    @Test
    public void placeOrder_MoreThanOneItemWithStocksExceptOne(){
        OrderItem item1 = new OrderItem();
        item1.setProductId("shirt");
        item1.setQuantity(2);
        item1.setPrice(16000);
        OrderItem item2 = new OrderItem();
        item2.setProductId("cap");
        item2.setQuantity(1);
        item2.setPrice(12000);
        OrderItem item3 = new OrderItem();
        item3.setProductId("trousers");
        item3.setQuantity(3);
        item3.setPrice(20000);
        List<OrderItem> itemsList = List.of(item1, item2, item3);
        String customerId = "test";

        when(inventoryClientMock.hasStock(item1.getProductId(), item1.getQuantity())).thenReturn(true);
        when(inventoryClientMock.hasStock(item2.getProductId(), item2.getQuantity())).thenReturn(false);
        
        Assertions.assertThrows(InsufficientStockException.class, () -> {
            orderService.placeOrder(customerId, itemsList);
        });
        
        verify(inventoryClientMock, never()).hasStock(item3.getProductId(), item3.getQuantity());
        verify(inventoryClientMock, never()).reserveStock(anyString(), anyInt());
        verifyNoInteractions(orderRepositoryMock);
        verifyNoInteractions(notificationSenderMock);
    }

    @Test
    public void placeOrder_EmptyList(){
        List<OrderItem> itemsList = Collections.emptyList();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrder("test", itemsList);
        });

        verify(inventoryClientMock, never()).hasStock(anyString(), anyInt());
        verify(inventoryClientMock, never()).reserveStock(anyString(), anyInt());
        verify(notificationSenderMock, never()).sendOrderConfirmation(anyString(), anyString());
        verify(orderRepositoryMock, never()).save(any(Order.class));
    }

    @Test
    public void placeOrder_NullArgument(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrder("test", null);
        });

        verify(inventoryClientMock, never()).hasStock(anyString(), anyInt());
        verify(inventoryClientMock, never()).reserveStock(anyString(), anyInt());
        verify(notificationSenderMock, never()).sendOrderConfirmation(anyString(), anyString());
        verify(orderRepositoryMock, never()).save(any(Order.class));
    }
}
