package com.example.Aisian.Cuisine.dto;

import com.example.Aisian.Cuisine.model.Product;

import java.util.List;

public class ProductDetailResponse {
    private Product product;
    private List<String> images; // sửa ở đây
    private List<Product> relatedProducts;

    public ProductDetailResponse(Product product, List<String> images, List<Product> relatedProducts) {
        this.product = product;
        this.images = images;
        this.relatedProducts = relatedProducts;
    }

    // Getter và Setter
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Product> getRelatedProducts() {
        return relatedProducts;
    }

    public void setRelatedProducts(List<Product> relatedProducts) {
        this.relatedProducts = relatedProducts;
    }
}
