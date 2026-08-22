package com.hecatesmoon.testingmockmvcexercise.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String msg){
        super(msg);
    }
}
