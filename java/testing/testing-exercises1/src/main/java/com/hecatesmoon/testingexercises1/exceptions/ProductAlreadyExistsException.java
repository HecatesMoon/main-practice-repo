package com.hecatesmoon.testingexercises1.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException (String msg) {
        super(msg);
    }
}
