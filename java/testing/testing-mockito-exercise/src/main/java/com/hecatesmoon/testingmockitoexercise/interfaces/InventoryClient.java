package com.hecatesmoon.testingmockitoexercise.interfaces;

public interface InventoryClient {
    boolean hasStock(String productId, int quantity);
    void reserveStock(String productId, int quantity);
}