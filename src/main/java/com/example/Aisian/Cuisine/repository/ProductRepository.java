package com.example.Aisian.Cuisine.repository;

import com.example.Aisian.Cuisine.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Tìm sản phẩm theo tên với phân trang
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // ✅ Sửa đúng kiểu dữ liệu là Long
    List<Product> findTop5ByCategoryIdAndIdNot(Long categoryId, Long excludeId);
}
