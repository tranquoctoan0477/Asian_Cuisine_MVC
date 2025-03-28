package com.example.Aisian.Cuisine.dto;

import java.util.List;

import com.example.Aisian.Cuisine.model.Category;
import com.example.Aisian.Cuisine.model.Product;

public class HomeResponse {
    private List<Category> categories;
    private List<Product> allProducts;
    private List<Product> newProducts;
    private List<Product> promoProducts;

    public HomeResponse() {
    }

    public HomeResponse(List<Category> categories, List<Product> allProducts,
                        List<Product> newProducts, List<Product> promoProducts) {
        this.categories = categories;
        this.allProducts = allProducts;
        this.newProducts = newProducts;
        this.promoProducts = promoProducts;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public List<Product> getNewProducts() {
        return newProducts;
    }

    public void setNewProducts(List<Product> newProducts) {
        this.newProducts = newProducts;
    }

    public List<Product> getPromoProducts() {
        return promoProducts;
    }

    public void setPromoProducts(List<Product> promoProducts) {
        this.promoProducts = promoProducts;
    }
}
