package com.example.Aisian.Cuisine.dto;

public class CartUpdateRequestDTO {
    private Long productId;
    private int quantity;

    public CartUpdateRequestDTO() {}

    public CartUpdateRequestDTO(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

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
