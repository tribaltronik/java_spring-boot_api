package com.example.pocapi.controller;

import com.example.pocapi.dto.ProductDto;
import com.example.pocapi.model.Product;
import com.example.pocapi.security.JwtTokenProvider;
import com.example.pocapi.service.CustomUserDetailsService;
import com.example.pocapi.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getAllProductsReturnsListOfProducts() throws Exception {
        Product product1 = new Product("Laptop", "Powerful laptop", BigDecimal.valueOf(1200.00));
        product1.setId(1L);
        Product product2 = new Product("Mouse", "Wireless mouse", BigDecimal.valueOf(25.00));
        product2.setId(2L);

        Page<Product> page = new PageImpl<>(Arrays.asList(product1, product2));
        when(productService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Laptop"))
                .andExpect(jsonPath("$.content[1].name").value("Mouse"));
    }

    @Test
    @WithMockUser
    void getProductByIdReturnsProductWhenFound() throws Exception {
        Product product = new Product("Keyboard", "Mechanical keyboard", BigDecimal.valueOf(75.00));
        product.setId(1L);
        when(productService.findById(1L)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    @Test
    @WithMockUser
    void getProductByIdReturnsNotFoundWhenNotFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void createProductReturnsCreatedProduct() throws Exception {
        ProductDto productDto = new ProductDto(null, "Monitor", "4K Monitor", BigDecimal.valueOf(300.00));
        Product product = new Product("Monitor", "4K Monitor", BigDecimal.valueOf(300.00));
        product.setId(1L);

        when(productService.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Monitor"));
    }

    @Test
    @WithMockUser
    void updateProductReturnsUpdatedProduct() throws Exception {
        ProductDto productDto = new ProductDto(1L, "Monitor Updated", "4K Monitor Updated", BigDecimal.valueOf(350.00));
        Product existingProduct = new Product("Monitor", "4K Monitor", BigDecimal.valueOf(300.00));
        existingProduct.setId(1L);
        Product updatedProduct = new Product("Monitor Updated", "4K Monitor Updated", BigDecimal.valueOf(350.00));
        updatedProduct.setId(1L);

        when(productService.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productService.save(any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Monitor Updated"));
    }

    @Test
    @WithMockUser
    void updateProductReturnsNotFoundWhenNotFound() throws Exception {
        ProductDto productDto = new ProductDto(1L, "Monitor Updated", "4K Monitor Updated", BigDecimal.valueOf(350.00));
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteProductReturnsNoContentWhenFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(new Product()));
        doNothing().when(productService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteProductReturnsNotFoundWhenNotFound() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
