package com.example.Aisian.Cuisine.dto;

public class CartUpdateRequestDTO {
    private Long productId;
    private int quantity;
    private String note; // ✅ Đã có biến note

    public CartUpdateRequestDTO() {}

    // 🔥 Thêm constructor đầy đủ
    public CartUpdateRequestDTO(Long productId, int quantity, String note) {
        this.productId = productId;
        this.quantity = quantity;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
