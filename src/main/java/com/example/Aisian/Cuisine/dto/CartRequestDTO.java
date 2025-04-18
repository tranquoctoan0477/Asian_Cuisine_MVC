package com.example.Aisian.Cuisine.dto;

public class CartRequestDTO {
    private Long productId;
    private int quantity;

    // Constructors
    public CartRequestDTO() {}

    public CartRequestDTO(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
