package com.example.Aisian.Cuisine.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "main_img")
    private String mainImg;

    @Column(name = "category_id")
    private Long categoryId; // ✅ đã sửa từ Integer → Long

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "voucher_code")
    private String voucherCode;

    // ===== Constructors =====
    public Product() {}

    public Product(Long id, String name, String description, BigDecimal price,
                   String mainImg, Long categoryId, Timestamp createdAt, String voucherCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.mainImg = mainImg;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.voucherCode = voucherCode;
    }

    // ===== Getters and Setters =====
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

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }
}
