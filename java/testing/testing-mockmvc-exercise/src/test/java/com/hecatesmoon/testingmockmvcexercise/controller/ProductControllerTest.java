package com.hecatesmoon.testingmockmvcexercise.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hecatesmoon.testingmockmvcexercise.dto.ProductRequestDTO;
import com.hecatesmoon.testingmockmvcexercise.dto.ProductResponseDTO;
import com.hecatesmoon.testingmockmvcexercise.service.ProductService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ProductService productService;

    public ProductControllerTest(){
    }

    @Test
    public void postProduct() throws Exception{
        ProductRequestDTO product = new ProductRequestDTO();
        product.setName("milk");
        product.setPrice(3.0);
        product.setStock(70L);
        ProductResponseDTO returnProduct = new ProductResponseDTO();
        returnProduct.setId(1L);
        returnProduct.setName("milk");
        returnProduct.setPrice(3.0);
        returnProduct.setStock(70L);

        when(productService.createProduct(any(ProductRequestDTO.class))).thenReturn(returnProduct);

        RequestBuilder request = post("/api/products")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(product));

        mockMvc.perform(request).andExpect(status().isCreated())
                                .andExpect(jsonPath("$.time").value(Matchers.containsString(LocalDate.now().toString())))
                                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
                                .andExpect(jsonPath("$.response.name").value("milk"))
                                .andExpect(jsonPath("$.response.price").value("3.0"))
                                .andExpect(jsonPath("$.response.stock").value("70"))
                                .andExpect(jsonPath("$.response.id").value("1"));
    }

    @Test
    public void getProduct() throws Exception{
        ProductResponseDTO product = new ProductResponseDTO();
        product.setId(1L);
        product.setName("juice");
        product.setPrice(2.0);
        product.setStock(120L);

        when(productService.getProductById(1L)).thenReturn(product);

        RequestBuilder request = get("/api/products/1");

        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.time").value(Matchers.containsString(LocalDate.now().toString())))
               .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
               .andExpect(jsonPath("$.response.id").value(1L))
               .andExpect(jsonPath("$.response.name").value("juice"))
               .andExpect(jsonPath("$.response.price").value(2.0))
               .andExpect(jsonPath("$.response.stock").value(120L));
    } 

}
