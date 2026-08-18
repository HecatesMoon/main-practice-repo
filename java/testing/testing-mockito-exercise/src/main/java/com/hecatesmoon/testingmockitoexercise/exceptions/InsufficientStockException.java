package com.hecatesmoon.testingmockitoexercise.exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException (String msg){
        super(msg);
    }
}
