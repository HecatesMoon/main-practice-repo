package com.hecatesmoon.testingmockmvcexercise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hecatesmoon.testingmockmvcexercise.dto.ProductRequestDTO;
import com.hecatesmoon.testingmockmvcexercise.dto.ProductResponseDTO;

@Service
public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO dto);
    ProductResponseDTO getProductById(Long id);
    List<ProductResponseDTO> listProducts(Double minPrice);
    void deleteProduct(Long id);
}
