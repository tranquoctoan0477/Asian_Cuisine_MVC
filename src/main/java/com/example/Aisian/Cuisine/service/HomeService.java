package com.example.Aisian.Cuisine.service;

import com.example.Aisian.Cuisine.dto.HomeResponse;
import com.example.Aisian.Cuisine.model.Category;
import com.example.Aisian.Cuisine.model.Product;
import com.example.Aisian.Cuisine.repository.CategoryRepository;
import com.example.Aisian.Cuisine.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // Cập nhật để hỗ trợ phân trang
    public HomeResponse getHomeData(String search, int page, int limit) {
        List<Category> categories = categoryRepository.findAll();

        // Phân trang dữ liệu sản phẩm
        Page<Product> productPage;
        if (search != null && !search.trim().isEmpty()) {
            productPage = productRepository.findByNameContainingIgnoreCase(search.trim(), PageRequest.of(page, limit));
        } else {
            productPage = productRepository.findAll(PageRequest.of(page, limit));
        }

        List<Product> allProducts = productPage.getContent();

        // Lọc sản phẩm mới (tạo trong 7 ngày gần nhất)
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        Timestamp threshold = Timestamp.valueOf(sevenDaysAgo);

        List<Product> newProducts = allProducts.stream()
                .filter(p -> p.getCreatedAt() != null && p.getCreatedAt().after(threshold))
                .collect(Collectors.toList());

        // Lọc sản phẩm có mã khuyến mãi
        List<Product> promoProducts = allProducts.stream()
                .filter(p -> p.getVoucherCode() != null && !p.getVoucherCode().isEmpty())
                .collect(Collectors.toList());

        return new HomeResponse(categories, allProducts, newProducts, promoProducts);
    }
}
