package com.hecatesmoon.testingexercises1.inventory;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hecatesmoon.testingexercises1.exceptions.ProductAlreadyExistsException;
import com.hecatesmoon.testingexercises1.exceptions.ProductNotFoundException;

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

        Assertions.assertTrue(newInventory.containsKey(product));
        Assertions.assertTrue(newInventory.get(product).equals(quantity));
    }

    @Test
    public void testAddProdduct_AlreadyExistingProduct(){
        String product = "apple pies";
        int quantity = 4;
        inventory.addProduct(product, quantity);

        Assertions.assertThrows(ProductAlreadyExistsException.class, () -> {
            inventory.addProduct(product, quantity);
        });
    }

    @Test
    public void testEditProductQty_ExistingProduct(){
        String product = "apple juice";
        int quantity = 12;
        int newValue = 6;
        inventory.addProduct(product, quantity);

        inventory.editProductQty(product, newValue);

        Assertions.assertTrue(inventory.getInventory().containsKey(product));
        Assertions.assertTrue(inventory.getInventory().get(product).equals(newValue));
    }

    @Test
    public void testEditProductQty_NonExistingProduct(){
        String product = "apple juice";
        int quantity = 12;
        String product2 = "strawberry cake";
        int quantity2 = 3;
        inventory.addProduct(product, quantity);

        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            inventory.editProductQty(product2, quantity2);
            });
    }
}
