package com.hecatesmoon.testingmockmvcexercise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hecatesmoon.testingmockmvcexercise.dto.ProductRequestDTO;
import com.hecatesmoon.testingmockmvcexercise.dto.ProductResponseDTO;
import com.hecatesmoon.testingmockmvcexercise.exception.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    Map<Long,ProductResponseDTO> products = new HashMap<>();
    Long id = 6L;

    public ProductServiceImpl (){
        ProductResponseDTO product1 = new ProductResponseDTO();
        product1.setId(1L);
        product1.setName("shoes");
        product1.setPrice(30);
        product1.setStock(15);
        ProductResponseDTO product2 = new ProductResponseDTO();
        product2.setId(2L);
        product2.setName("shirts");
        product2.setPrice(15);
        product2.setStock(30);
        ProductResponseDTO product3 = new ProductResponseDTO();
        product3.setId(3L);
        product3.setName("pants");
        product3.setPrice(20);
        product3.setStock(23);
        ProductResponseDTO product4 = new ProductResponseDTO();
        product4.setId(4L);
        product4.setName("caps");
        product4.setPrice(7);
        product4.setStock(12);
        ProductResponseDTO product5 = new ProductResponseDTO();
        product5.setId(5L);
        product5.setName("gloves");
        product5.setPrice(8);
        product5.setStock(10);
        ProductResponseDTO product6 = new ProductResponseDTO();
        product6.setId(6L);
        product6.setName("socks");
        product6.setPrice(5);
        product6.setStock(17);
        products.put(product1.getId(), product1);
        products.put(product2.getId(), product2);
        products.put(product3.getId(), product3);
        products.put(product4.getId(), product4);
        products.put(product5.getId(), product5);
        products.put(product6.getId(), product6);
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto){
        ProductResponseDTO product = new ProductResponseDTO();
        product.setId(id+1);
        id++;
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        products.put(product.getId(), product);
        return product;
    }

    public ProductResponseDTO editProduct(Long id, ProductRequestDTO dto){

        ProductResponseDTO product = products.get(id);

        if (product.equals(null)){
            throw new ProductNotFoundException("Product was not found, id:" + id);
        }

        ProductResponseDTO newProduct = new ProductResponseDTO();
        newProduct.setId(id);
        newProduct.setName(dto.getName());
        newProduct.setPrice(dto.getPrice());
        newProduct.setStock(dto.getStock());
        
        return products.put(id, newProduct);
    }

    public ProductResponseDTO getProductById(Long id){
        ProductResponseDTO response = products.get(id);

        if (response.equals(null)){
            throw new ProductNotFoundException("Product was not found, id:" + id);
        }

        return response;
    }

    public List<ProductResponseDTO> listProducts(Double minPrice){
        Map<Long, ProductResponseDTO> filteredProducts = new HashMap<>();

        products.keySet().stream()
                .filter(p -> products.get(p).getPrice()>=minPrice)
                .forEach(l -> filteredProducts.put(l, products.get(l)));

        List<ProductResponseDTO> list = new ArrayList<>(filteredProducts.values());
        return list;
    }

    public void deleteProduct(Long id){
        if (products.get(id).equals(null)){
            throw new ProductNotFoundException("Product was not found, id:" + id);
        }

        products.remove(id);
    }
}
