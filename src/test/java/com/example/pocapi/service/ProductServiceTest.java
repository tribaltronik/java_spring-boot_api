package com.example.pocapi.service;

import com.example.pocapi.model.Product;
import com.example.pocapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllReturnsListOfProducts() {
        Product product1 = new Product("Laptop", "Powerful laptop", BigDecimal.valueOf(1200.00));
        Product product2 = new Product("Mouse", "Wireless mouse", BigDecimal.valueOf(25.00));
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.findAll();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findByIdReturnsProductWhenFound() {
        Product product = new Product("Keyboard", "Mechanical keyboard", BigDecimal.valueOf(75.00));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findById(1L);

        assertTrue(foundProduct.isPresent());
        assertEquals("Keyboard", foundProduct.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdReturnsEmptyWhenNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.findById(1L);

        assertTrue(foundProduct.isEmpty());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void saveReturnsSavedProduct() {
        Product productToSave = new Product("Monitor", "4K Monitor", BigDecimal.valueOf(300.00));
        when(productRepository.save(productToSave)).thenReturn(productToSave);

        Product savedProduct = productService.save(productToSave);

        assertEquals("Monitor", savedProduct.getName());
        verify(productRepository, times(1)).save(productToSave);
    }

    @Test
    void deleteByIdDeletesProduct() {
        productService.deleteById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}
