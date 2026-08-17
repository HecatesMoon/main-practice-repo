package com.hecatesmoon.testingexercises1.inventory;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hecatesmoon.testingexercises1.exceptions.InsufficientStockException;
import com.hecatesmoon.testingexercises1.exceptions.InventoryIsEmptyException;
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

    @Test
    public void testReduceStock_PositiveNumber(){
        String product = "apple juice";
        int quantity = 12;
        inventory.addProduct(product, quantity);

        inventory.reduceStock(product, 2);

        Assertions.assertEquals(10, inventory.getInventory().get(product));
    }

    @Test
    public void testReduceStock_NegativeNumber(){
        String product = "apple juice";
        int quantity = 12;
        inventory.addProduct(product, quantity);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            inventory.reduceStock(product, -2);
        });
    }

    @Test
    public void testReduceStock_ReducingMoreThanActualStock(){
        String product = "apple juice";
        int quantity = 12;
        inventory.addProduct(product, quantity);

        Assertions.assertThrows(InsufficientStockException.class, () -> {
            inventory.reduceStock(product, 15);
        });
    }

    @Test
    public void testReduceStock_ProductDoesNotExist(){
        String product = "apple juice";
        int quantity = 12;
        inventory.addProduct(product, quantity);
        String product2 = "bread";
        int quantity2 = 20;

        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            inventory.reduceStock(product2, quantity2);
        });
    }

    @Test
    public void testLowStockProducts_SimpleList(){
        inventory.addProduct("apple pie", 12);
        inventory.addProduct("chocolate bar", 16);
        inventory.addProduct("milkshake", 7);
        inventory.addProduct("ice cream", 8);
        inventory.addProduct("melon pan", 10);

        List<String> result = inventory.lowStockProducts(10);
        List<String> expected = List.of("milkshake", "ice cream", "melon pan");

        Assertions.assertIterableEquals(expected, result);
    }

    @Test
    public void testLowStockProducts_EmptyList(){
       
        Assertions.assertThrows(InventoryIsEmptyException.class, () -> {
            inventory.lowStockProducts(4);
        });

    }
}
