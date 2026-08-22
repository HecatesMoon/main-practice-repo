package com.hecatesmoon.testingmockmvcexercise.service;

import java.util.List;

import com.hecatesmoon.testingmockmvcexercise.dto.ProductRequestDTO;
import com.hecatesmoon.testingmockmvcexercise.dto.ProductResponseDTO;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO dto);
    ProductResponseDTO editProduct(Long id, ProductRequestDTO dto);
    ProductResponseDTO getProductById(Long id);
    List<ProductResponseDTO> listProducts(Double minPrice);
    void deleteProduct(Long id);
}
