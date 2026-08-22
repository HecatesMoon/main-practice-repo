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
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.hecatesmoon.testingmockitoexercise.exceptions.InsufficientStockException;
import com.hecatesmoon.testingmockitoexercise.exceptions.OrderAlreadyCancelledException;
import com.hecatesmoon.testingmockitoexercise.exceptions.OrderNotFoundException;
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

        Order dummyOrder = new Order();
        dummyOrder.setId("order-999");
        Order resultDummyOrder;
        
        when(inventoryClientMock.hasStock("shoes", 1)).thenReturn(true);
        when(orderRepositoryMock.save(any(Order.class))).thenReturn(dummyOrder);
        
        resultDummyOrder = orderService.placeOrder("test", List.of(item));
        
        verify(inventoryClientMock, times(1)).reserveStock(item.getProductId(), item.getQuantity());
        verify(orderRepositoryMock, times(1)).save(order.capture());
        verify(notificationSenderMock, times(1)).sendOrderConfirmation("test", dummyOrder.getId());

        Assertions.assertEquals(OrderStatus.CONFIRMED, order.getValue().getStatus());
        Assertions.assertEquals(40000, order.getValue().getTotal());
        Assertions.assertEquals(dummyOrder, resultDummyOrder);
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
        Order dummyOrder = new Order();
        dummyOrder.setId("order-999");

        when(inventoryClientMock.hasStock(item1.getProductId(), item1.getQuantity())).thenReturn(true);
        when(inventoryClientMock.hasStock(item2.getProductId(), item2.getQuantity())).thenReturn(true);
        when(inventoryClientMock.hasStock(item3.getProductId(), item3.getQuantity())).thenReturn(true);
        when(orderRepositoryMock.save(any(Order.class))).thenReturn(dummyOrder);

        Order resultDummyOrder = orderService.placeOrder(customerId, itemsList);

        verify(inventoryClientMock, times(1)).reserveStock(item1.getProductId(), item1.getQuantity());
        verify(inventoryClientMock, times(1)).reserveStock(item2.getProductId(), item2.getQuantity());
        verify(inventoryClientMock, times(1)).reserveStock(item3.getProductId(), item3.getQuantity());
        verify(orderRepositoryMock, times(1)).save(order.capture());
        verify(notificationSenderMock, times(1)).sendOrderConfirmation(customerId, dummyOrder.getId());

        Assertions.assertEquals(104000, order.getValue().getTotal());
        Assertions.assertEquals(OrderStatus.CONFIRMED, order.getValue().getStatus());
        Assertions.assertEquals(dummyOrder, resultDummyOrder);
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
    public void placeOrder_ItemsWithDecimalPrices(){
        OrderItem item1 = new OrderItem();
        item1.setProductId("shirt");
        item1.setQuantity(2);
        item1.setPrice(15999.99);
        OrderItem item2 = new OrderItem();
        item2.setProductId("cap");
        item2.setQuantity(1);
        item2.setPrice(11500.40);
        OrderItem item3 = new OrderItem();
        item3.setProductId("trousers");
        item3.setQuantity(3);
        item3.setPrice(18000.33);
        List<OrderItem> itemsList = List.of(item1, item2, item3);

        String customerId = "test";
        ArgumentCaptor<Order> order = ArgumentCaptor.forClass(Order.class);
        Order dummyOrder = new Order();
        dummyOrder.setId("order-999");

        when(inventoryClientMock.hasStock(item1.getProductId(), item1.getQuantity())).thenReturn(true);
        when(inventoryClientMock.hasStock(item2.getProductId(), item2.getQuantity())).thenReturn(true);
        when(inventoryClientMock.hasStock(item3.getProductId(), item3.getQuantity())).thenReturn(true);
        when(orderRepositoryMock.save(any(Order.class))).thenReturn(dummyOrder);

        Order resultDummyOrder = orderService.placeOrder(customerId, itemsList);

        verify(inventoryClientMock, times(1)).reserveStock(item1.getProductId(), item1.getQuantity());
        verify(inventoryClientMock, times(1)).reserveStock(item2.getProductId(), item2.getQuantity());
        verify(inventoryClientMock, times(1)).reserveStock(item3.getProductId(), item3.getQuantity());
        verify(orderRepositoryMock, times(1)).save(order.capture());
        verify(notificationSenderMock, times(1)).sendOrderConfirmation(customerId, dummyOrder.getId());

        Assertions.assertEquals(97501.37, order.getValue().getTotal());
        Assertions.assertEquals(OrderStatus.CONFIRMED, order.getValue().getStatus());
        Assertions.assertEquals(dummyOrder, resultDummyOrder);
    }

    @Test
    public void placeOrder_EmptyList(){
        List<OrderItem> itemsList = Collections.emptyList();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrder("test", itemsList);
        });

        verifyNoInteractions(inventoryClientMock);
        verifyNoInteractions(notificationSenderMock);
        verifyNoInteractions(orderRepositoryMock);
    }

    @Test
    public void placeOrder_NullArgument(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrder("test", null);
        });

        verifyNoInteractions(inventoryClientMock);
        verifyNoInteractions(notificationSenderMock);
        verifyNoInteractions(orderRepositoryMock);
    }

    @Test
    public void cancelOrder_CancelConfirmedOrder(){
        Order order = new Order();
        order.setStatus(OrderStatus.CONFIRMED);
        order.setCustomerId("test");
        String orderId = "order-123";
        
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));
        
        orderService.cancelOrder(orderId);

        verify(orderRepositoryMock, times(1)).save(order);
        verify(notificationSenderMock, times(1)).sendCancellationNotice(order.getCustomerId(), order.getId());

        Assertions.assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void cancelOrder_CancelPendingOrder(){
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setCustomerId("test");
        String orderId = "order-123";
        
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));
        
        orderService.cancelOrder(orderId);

        verify(orderRepositoryMock, times(1)).save(order);
        verify(notificationSenderMock, times(1)).sendCancellationNotice(order.getCustomerId(), order.getId());

        Assertions.assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    public void cancelOrder_OrderDoesNotExistOrNull(){
        String orderId = "order-123";

        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.empty());

        Assertions.assertThrows(OrderNotFoundException.class, () -> {
            orderService.cancelOrder(orderId);
        });

        verify(orderRepositoryMock, never()).save(any(Order.class));
        verifyNoInteractions(notificationSenderMock);
    }

    @Test
    public void cancelOrder_OrderAlreadyCancelled(){
        Order order = new Order();
        order.setStatus(OrderStatus.CANCELLED);
        String orderId = "order-123";

        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

        Assertions.assertThrows(OrderAlreadyCancelledException.class, () -> {
            orderService.cancelOrder(orderId);
        });

        verify(orderRepositoryMock, never()).save(any(Order.class));
        verifyNoInteractions(notificationSenderMock);
    }

    @Test
    public void getOrder_OrderExists(){
        String orderId = "order-234";
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId("test");

        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

        Order resultOrder = orderService.getOrder(orderId);

        Assertions.assertEquals(order, resultOrder);
    }

    @Test
    public void getOrder_OrderDoesNotExist(){
        String orderId =  "order-322";
        
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.empty());

        Assertions.assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrder(orderId);
        });
    }

    @Test
    public void getOrdersByCustomer_OneOrderAndConfirmed(){
        Order order1 = new Order();
        order1.setStatus(OrderStatus.CONFIRMED);
        List<Order> ordersList = List.of(order1);
        List<Order> confirmedOrdersList = List.of(order1);
        String customerId = "test";

        when(orderRepositoryMock.findByCustomerId(customerId)).thenReturn(ordersList);

        List<Order> result = orderService.getOrdersByCustomer(customerId);

        Assertions.assertIterableEquals(confirmedOrdersList, result);
    }

    @Test
    public void getOrdersByCustomer_OneOrderNotConfirmed(){
        Order order1 = new Order();
        order1.setStatus(OrderStatus.PENDING);
        List<Order> ordersList = List.of(order1);
        List<Order> confirmedOrdersList = Collections.emptyList();
        String customerId = "test";

        when(orderRepositoryMock.findByCustomerId(customerId)).thenReturn(ordersList);

        List<Order> result = orderService.getOrdersByCustomer(customerId);

        Assertions.assertIterableEquals(confirmedOrdersList, result);
    }

    @Test
    public void getOrdersByCustomer_AllOrdersConfirmed(){
        Order order1 = new Order();
        order1.setStatus(OrderStatus.CONFIRMED);
        Order order2 = new Order();
        order2.setStatus(OrderStatus.CONFIRMED);
        Order order3 = new Order();
        order3.setStatus(OrderStatus.CONFIRMED);
        Order order4 = new Order();
        order4.setStatus(OrderStatus.CONFIRMED);
        Order order5 = new Order();
        order5.setStatus(OrderStatus.CONFIRMED);
        List<Order> ordersList = List.of(order1, order2, order3, order4, order5);
        List<Order> confirmedOrdersList = List.of(order1, order2, order3, order4, order5);
        String customerId = "test";

        when(orderRepositoryMock.findByCustomerId(customerId)).thenReturn(ordersList);

        List<Order> result = orderService.getOrdersByCustomer(customerId);

        Assertions.assertIterableEquals(confirmedOrdersList, result);
    }

    @Test
    public void getOrdersByCustomer_NotAllOrdersConfirmed(){
        Order order1 = new Order();
        order1.setStatus(OrderStatus.CONFIRMED);
        Order order2 = new Order();
        order2.setStatus(OrderStatus.CONFIRMED);
        Order order3 = new Order();
        order3.setStatus(OrderStatus.PENDING);
        Order order4 = new Order();
        order4.setStatus(OrderStatus.CONFIRMED);
        Order order5 = new Order();
        order5.setStatus(OrderStatus.CANCELLED);
        List<Order> ordersList = List.of(order1, order2, order3, order4, order5);
        List<Order> confirmedOrdersList = List.of(order1, order2, order4);
        String customerId = "test";

        when(orderRepositoryMock.findByCustomerId(customerId)).thenReturn(ordersList);

        List<Order> result = orderService.getOrdersByCustomer(customerId);

        Assertions.assertIterableEquals(confirmedOrdersList, result);
    }

    @Test
    public void getOrdersByCustomer_NoOrders(){
        List<Order> ordersList = Collections.emptyList();
        List<Order> confirmedOrdersList = Collections.emptyList();
        String customerId = "test";

        when(orderRepositoryMock.findByCustomerId(customerId)).thenReturn(ordersList);

        List<Order> result = orderService.getOrdersByCustomer(customerId);

        Assertions.assertIterableEquals(confirmedOrdersList, result);
    }
}
