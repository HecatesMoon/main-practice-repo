package com.hecatesmoon.testingmockitoexercise.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String msg){
        super(msg);
    }
}
