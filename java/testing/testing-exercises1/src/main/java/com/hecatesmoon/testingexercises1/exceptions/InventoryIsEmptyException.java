package com.hecatesmoon.testingexercises1.exceptions;

public class InventoryIsEmptyException extends RuntimeException {
    public InventoryIsEmptyException (String msg){
        super(msg);
    }
}
