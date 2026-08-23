package com.hecatesmoon.testingmockmvcexercise.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    public void getProduct() throws Exception{
        ProductResponseDTO product = new ProductResponseDTO();
        product.setId(1L);
        product.setName("juice");
        product.setPrice(2.0);
        product.setStock(120L);

        when(productService.getProductById(1L)).thenReturn(product);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/products/1");

        mockMvc.perform(request)
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.time").value(Matchers.containsString(LocalDate.now().toString())))
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.value()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(1L))
               .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value("juice"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(2.0))
               .andExpect(MockMvcResultMatchers.jsonPath("$.response.stock").value(120L));
    } //Import static

}
