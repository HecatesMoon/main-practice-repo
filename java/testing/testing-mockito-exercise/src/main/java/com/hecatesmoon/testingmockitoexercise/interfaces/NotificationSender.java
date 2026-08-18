package com.hecatesmoon.testingmockitoexercise.interfaces;

public interface NotificationSender {
    void sendOrderConfirmation(String customerId, String orderId);
    void sendCancellationNotice(String customerId, String orderId);
}
