package com.hecatesmoon.testingexercises1.inventory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hecatesmoon.testingexercises1.exceptions.InsufficientStockException;
import com.hecatesmoon.testingexercises1.exceptions.ProductNotFoundException;

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

    public void reduceStock(String product, int qty){
        if (!inventory.containsKey(product)){
            throw new ProductNotFoundException("this product does not exist: " + product);
        }

        if (qty > inventory.get(product)){
            throw new InsufficientStockException("you are trying to reduce more stock than available " + inventory.get(product));
        }

        int newValue = inventory.get(product) - qty;

        inventory.put(product, newValue);
    }

    public List<String> lowStockProducts(int threshold){
        if (inventory.isEmpty()){
            System.out.println("the inventory is empty");
        }
        return inventory.keySet().stream().filter(p -> inventory.get(p) <= threshold).toList();
    }

    public List<String> productsOrderedByQty(){
        if (inventory.isEmpty()){
            System.out.println("the inventory is empty");
        }
        return inventory.keySet().stream().sorted(Comparator.comparing(p -> inventory.get(p))).toList();
    }

    public Map<String, Integer> getInventory(){
        return inventory;
    }

}
