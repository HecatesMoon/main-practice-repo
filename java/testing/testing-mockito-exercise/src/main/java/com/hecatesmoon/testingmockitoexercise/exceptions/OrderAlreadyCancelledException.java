package com.hecatesmoon.testingmockitoexercise.exceptions;

public class OrderAlreadyCancelledException extends RuntimeException {
    public OrderAlreadyCancelledException (String msg){
        super(msg);
    }
}
