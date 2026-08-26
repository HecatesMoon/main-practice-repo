package com.hecatesmoon.testingmockmvcexercise.controller;

import org.springframework.web.bind.annotation.RestController;

import com.hecatesmoon.testingmockmvcexercise.dto.ProductRequestDTO;
import com.hecatesmoon.testingmockmvcexercise.dto.ProductResponseDTO;
import com.hecatesmoon.testingmockmvcexercise.service.ProductService;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RequestMapping("/api")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService service){
        this.productService = service;
    }

    @PostMapping("/products")
    public ResponseEntity<Map<String, Object>> addProduct(@Valid @RequestBody ProductRequestDTO request) {
        ProductResponseDTO product = productService.createProduct(request);

        return buildResponse(product, HttpStatus.CREATED);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Map<String, Object>> getProduct (@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return buildResponse(product, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> listAllProducts(@RequestParam(required = false) Double minPrice) {
        List<ProductResponseDTO> productsList;
        if (minPrice == null){
            productsList = productService.listProducts(0.0);
        } else {
            productsList = productService.listProducts(minPrice);
        }
        return buildResponse(productsList, HttpStatus.OK);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Map<String,Object>> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return buildResponse("product deleted successfully", HttpStatus.OK);
    }

    @PutMapping("products/{id}")
    public ResponseEntity<Map<String, Object>> putMethodName(@PathVariable Long id, @Valid @RequestBody  ProductRequestDTO product) {
        ProductResponseDTO response = productService.editProduct(id, product);        
        return buildResponse(response, HttpStatus.OK);
    }

    private ResponseEntity<Map<String,Object>> buildResponse(Object object, HttpStatusCode status){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("time", LocalDateTime.now());
        response.put("status", status.value());
        response.put("response", object);
        return new ResponseEntity<Map<String,Object>>(response, status);
    }
}
