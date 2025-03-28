package com.example.Aisian.Cuisine.service;

import com.example.Aisian.Cuisine.dto.ProductDetailResponse;
import com.example.Aisian.Cuisine.model.Product;
import com.example.Aisian.Cuisine.model.ProductVariant;
import com.example.Aisian.Cuisine.repository.ProductRepository;
import com.example.Aisian.Cuisine.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository variantRepository;

    public ProductDetailResponse getProductDetailById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // Lấy ảnh từ bảng product_variants
        List<String> images = variantRepository.findByProductId(productId)
                .stream()
                .map(ProductVariant::getThumbnail)
                .collect(Collectors.toList());

        // ✅ Thêm ảnh chính (mainImg) vào đầu danh sách
        if (product.getMainImg() != null && !product.getMainImg().isEmpty()) {
            images.add(0, product.getMainImg());
        }

        // Lấy sản phẩm liên quan (cùng category)
        List<Product> related = productRepository.findTop5ByCategoryIdAndIdNot(product.getCategoryId(), productId);

        return new ProductDetailResponse(product, images, related);
    }
}
