package com.hecatesmoon.testingmockmvcexercise.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hecatesmoon.testingmockmvcexercise.dto.ProductRequestDTO;
import com.hecatesmoon.testingmockmvcexercise.dto.ProductResponseDTO;
import com.hecatesmoon.testingmockmvcexercise.exception.ProductNotFoundException;
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
    public void postProduct_ValidProduct() throws Exception{
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

    @ParameterizedTest
    @CsvSource({" ,1.0,20",
                "pear,0.0,20",
                "watermelon,-4.0,22",
                "orange,5.0,-12"
    })
    public void postProduct_ValidationNotPassing(String name, double price, long stock) throws Exception{
        ProductRequestDTO product = new ProductRequestDTO();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        RequestBuilder request = post("/api/products")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .content(objectMapper.writeValueAsString(product));
                
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

        verify(productService, never()).createProduct(any(ProductRequestDTO.class));
    }

    @Test
    public void getProduct_ValidId() throws Exception{
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

    @Test
    public void getProduct_InvalidId() throws Exception{
        when(productService.getProductById(anyLong())).thenThrow(new ProductNotFoundException("Product not Found"));

        RequestBuilder request = get("/api/products/12");

        mockMvc.perform(request)
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.time").value(Matchers.containsString(LocalDateTime.now().toString().substring(0, 15))))
               .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
               .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void listAllProducts_withValidParam() throws Exception{
        ProductResponseDTO product1 = new ProductResponseDTO();
        product1.setId(1L);
        product1.setName("milk");
        product1.setPrice(3.0);
        product1.setStock(40L);
        ProductResponseDTO product2 = new ProductResponseDTO();
        product2.setId(2L);
        product2.setName("eggs");
        product2.setPrice(0.6);
        product2.setStock(90L);
        ProductResponseDTO product3 = new ProductResponseDTO();
        product3.setId(3L);
        product3.setName("cheese");
        product3.setPrice(2.0);
        product3.setStock(80L);

        when(productService.listProducts(1.0)).thenReturn(List.of(product1,product3));

        RequestBuilder request = get("/api/products").param("minPrice","1");

        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.time").value(Matchers.containsString(LocalDateTime.now().toString().substring(0,15))))
               .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
               .andExpect(jsonPath("$.response", Matchers.hasSize(2)))
               .andExpect(jsonPath("$.response[0].id").value("1"))
               .andExpect(jsonPath("$.response[0].name").value("milk"))
               .andExpect(jsonPath("$.response[0].price").value("3.0"))
               .andExpect(jsonPath("$.response[0].stock").value("40"))
               .andExpect(jsonPath("$.response[1].id").value("3"))
               .andExpect(jsonPath("$.response[1].name").value("cheese"))
               .andExpect(jsonPath("$.response[1].price").value("2.0"))
               .andExpect(jsonPath("$.response[1].stock").value("80"));

        verify(productService, never()).listProducts(0.0); //this part only makes sense because in this test we do not use 0.0 as minPrice
    }
    
    @Test
    public void listAllProducts_withEmptyParam() throws Exception{
        ProductResponseDTO product1 = new ProductResponseDTO();
        product1.setId(1L);
        product1.setName("milk");
        product1.setPrice(3.0);
        product1.setStock(40L);
        ProductResponseDTO product2 = new ProductResponseDTO();
        product2.setId(2L);
        product2.setName("eggs");
        product2.setPrice(0.6);
        product2.setStock(90L);
        ProductResponseDTO product3 = new ProductResponseDTO();
        product3.setId(3L);
        product3.setName("cheese");
        product3.setPrice(2.0);
        product3.setStock(80L);

        when(productService.listProducts(any())).thenReturn(List.of(product1,product2,product3));

        RequestBuilder request = get("/api/products");

        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.time").value(Matchers.containsString(LocalDateTime.now().toString().substring(0,15))))
               .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
               .andExpect(jsonPath("$.response", Matchers.hasSize(3)))
               .andExpect(jsonPath("$.response[0].id").value("1"))
               .andExpect(jsonPath("$.response[0].name").value("milk"))
               .andExpect(jsonPath("$.response[0].price").value("3.0"))
               .andExpect(jsonPath("$.response[0].stock").value("40"))
               .andExpect(jsonPath("$.response[1].id").value("2"))
               .andExpect(jsonPath("$.response[1].name").value("eggs"))
               .andExpect(jsonPath("$.response[1].price").value("0.6"))
               .andExpect(jsonPath("$.response[1].stock").value("90"))
               .andExpect(jsonPath("$.response[2].id").value("3"))
               .andExpect(jsonPath("$.response[2].name").value("cheese"))
               .andExpect(jsonPath("$.response[2].price").value("2.0"))
               .andExpect(jsonPath("$.response[2].stock").value("80"));
    }

    @Test
    public void deleteProduct_ValidId() throws Exception{
        RequestBuilder request = delete("/api/products/1");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time").value(Matchers.containsString(LocalDateTime.now().toString().substring(0, 15))))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.response").exists());

        verify(productService, times(1)).deleteProduct(1L);
    }

}
