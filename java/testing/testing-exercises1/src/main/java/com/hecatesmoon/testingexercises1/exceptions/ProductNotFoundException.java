package com.hecatesmoon.testingexercises1.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String msg){
        super(msg);
    }

}