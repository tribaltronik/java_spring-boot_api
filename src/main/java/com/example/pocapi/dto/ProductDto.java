package com.example.pocapi.dto;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;

    @javax.validation.constraints.NotBlank(message = "Name is required")
    @javax.validation.constraints.Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @javax.validation.constraints.Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @javax.validation.constraints.NotNull(message = "Price is required")
    @javax.validation.constraints.Positive(message = "Price must be positive")
    private BigDecimal price;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
