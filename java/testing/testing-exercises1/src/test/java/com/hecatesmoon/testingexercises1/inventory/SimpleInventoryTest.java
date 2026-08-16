package com.hecatesmoon.testingexercises1.inventory;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleInventoryTest {

    private SimpleInventory inventory;
    
    public SimpleInventoryTest(){}

    @BeforeEach
    public void newInventory(){
        inventory = new SimpleInventory();
    }

    @Test
    public void testAddProduct_NewProduct(){
        String product = "cookies";
        int quantity = 12;

        inventory.addProduct(product, quantity);
        Map<String, Integer> newInventory = inventory.getInventory();

        Assertions.assertTrue((newInventory.containsKey(product) && 
                                newInventory.get(product).equals(quantity)));
    }
}
