package com.hecatesmoon.testingmockmvcexercise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hecatesmoon.testingmockmvcexercise.dto.ProductRequestDTO;
import com.hecatesmoon.testingmockmvcexercise.dto.ProductResponseDTO;
import com.hecatesmoon.testingmockmvcexercise.exception.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    List<ProductResponseDTO> list = new ArrayList<>();
    static Long id = 5L;

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
        list.add(product1);
        list.add(product2);
        list.add(product3);
        list.add(product4);
        list.add(product5);
        list.add(product6);
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto){
        ProductResponseDTO product = new ProductResponseDTO();
        product.setId(ProductServiceImpl.id+1);
        ProductServiceImpl.id++;
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        list.add(product);
        return product;
    };
    public ProductResponseDTO editProduct(Long id, ProductRequestDTO dto){

        if (findById(id)==null){
            throw new ProductNotFoundException("Product was not found, id:" + id);
        }

        int i = findIndexById(id);

        list.get(i).setName(dto.getName());
        list.get(i).setPrice(dto.getPrice());
        list.get(i).setStock(dto.getStock());

        return list.get(i);
    };
    public ProductResponseDTO getProductById(Long id){
        ProductResponseDTO response = findById(id);

        if (response==null){
            throw new ProductNotFoundException("Product was not found, id:" + id);
        }

        return response;
    };
    public List<ProductResponseDTO> listProducts(Double minPrice){
        return list.stream().filter(p -> p.getPrice()>=minPrice).toList();
    };
    public void deleteProduct(Long id){
        if (findById(id)==null){
            throw new ProductNotFoundException("Product was not found, id:" + id);
        }

        int i = findIndexById(id);

        list.remove(i);
    };

    private ProductResponseDTO findById(Long id){
        for (ProductResponseDTO product : list){
            if (id.equals(product.getId())){
                return product;
            }
        }
        return null;
    }
    private int findIndexById(Long id){
        int index = 0;
        for (ProductResponseDTO product : list){
            if (id.equals(product.getId())){
                return index;
            } else {
                index++;
            }
        }
        return -1;
    }
}
