package com.example.Aisian.Cuisine.model;

import jakarta.persistence.*;

@Entity
@Table (name = "product_variants")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // Liên kết với Product

    private String thumbnail;  // Cột thumbnail
    private boolean isPrimary; // Cột is_primary

    // Constructor mặc định
    public ProductVariant() {}

    // Constructor có tham số
    public ProductVariant(Long id, Product product, String thumbnail, boolean isPrimary) {
        this.id = id;
        this.product = product;
        this.thumbnail = thumbnail;
        this.isPrimary = isPrimary;
    }

    // Getter và Setter cho tất cả các trường
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
}
