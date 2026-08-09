package com.hecatesmoon.testingexercises1.inventory;

import java.util.HashMap;
import java.util.Map;

public class SimpleInventory {
    
    private Map<String, Integer> inventory = new HashMap<>();

    public SimpleInventory(){}

    public void addProduct(String product, int qty){
        if (inventory.containsKey(product)){
            System.out.println("This product already exists");
            return;
        }
        inventory.put(product, qty);
    }

    public void editProductQty(String product, int qty){
        inventory.put(product, qty);
    }



}
