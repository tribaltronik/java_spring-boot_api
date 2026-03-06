package com.example.pocapi.controller;

import com.example.pocapi.dto.ProductDto;
import com.example.pocapi.model.Product;
import com.example.pocapi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<ProductDto> products = productService.findAll(pageable)
                .map(this::convertToDto);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = convertToEntity(productDto);
        return convertToDto(productService.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        return productService.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDto.getName());
                    existingProduct.setDescription(productDto.getDescription());
                    existingProduct.setPrice(productDto.getPrice());
                    return convertToDto(productService.save(existingProduct));
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ProductDto convertToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    private Product convertToEntity(ProductDto productDto) {
        return new Product(productDto.getName(), productDto.getDescription(), productDto.getPrice());
    }
}
