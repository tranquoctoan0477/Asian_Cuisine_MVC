package com.example.Aisian.Cuisine.dto;

import java.io.Serializable;

public class CheckoutItemDTO implements Serializable {

    private Long productId;
    private String productName;
    private int quantity;
    private String note; // ✅ Thêm trường này để gửi ghi chú từ client
    private double price;
    private String thumbnail;

    public CheckoutItemDTO() {
    }

    public CheckoutItemDTO(Long productId, String productName, int quantity, String note, double price, String thumbnail) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.note = note; // ✅ Lưu `note`
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note; // ✅ Lưu `note`
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

