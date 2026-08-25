package com.hecatesmoon.testingmockmvcexercise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductRequestDTO {
    @NotBlank
    private String name;
    @Positive
    private double price;
    @PositiveOrZero
    private long stock;

    public ProductRequestDTO (){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

}
