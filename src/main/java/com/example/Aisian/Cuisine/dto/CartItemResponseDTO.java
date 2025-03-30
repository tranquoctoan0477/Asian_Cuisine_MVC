package com.example.Aisian.Cuisine.dto;

import java.math.BigDecimal;

public class CartItemResponseDTO {
    private Long productId;
    private String productName;
    private String thumbnail;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;
    private String note;

    // Constructors, getters, setters
    public CartItemResponseDTO() {}

    public CartItemResponseDTO(Long productId, String productName, String thumbnail, BigDecimal price, int quantity, BigDecimal subtotal, String note) {
        this.productId = productId;
        this.productName = productName;
        this.thumbnail = thumbnail;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.note = note; // Khởi tạo note
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
