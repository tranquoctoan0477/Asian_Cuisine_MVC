package com.example.Aisian.Cuisine.controller;

import com.example.Aisian.Cuisine.dto.ProductDetailResponse;
import com.example.Aisian.Cuisine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // API xem chi tiết sản phẩm
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable long id) {
        try {
            ProductDetailResponse response = productService.getProductDetailById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Không tìm thấy sản phẩm với ID: " + id);
        }
    }

}
