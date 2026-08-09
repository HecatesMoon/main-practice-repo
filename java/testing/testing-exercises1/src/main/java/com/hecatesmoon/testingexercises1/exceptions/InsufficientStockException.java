package com.hecatesmoon.testingexercises1.exceptions;

public class InsufficientStockException extends RuntimeException {
    
    public InsufficientStockException (String msg){
        super(msg);
    }
}
