package com.example.Aisian.Cuisine.service;

import com.example.Aisian.Cuisine.dto.*;
import com.example.Aisian.Cuisine.model.Order;
import com.example.Aisian.Cuisine.model.OrderItem;
import com.example.Aisian.Cuisine.model.Product;
import com.example.Aisian.Cuisine.repository.OrderItemRepository;
import com.example.Aisian.Cuisine.repository.OrderRepository;
import com.example.Aisian.Cuisine.repository.ProductRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addToCart(Long userId, CartRequestDTO request) {
        // 1. Tìm đơn hàng 'pending' của user
        Optional<Order> optionalOrder = orderRepository.findByUserIdAndStatus(userId, "pending");
        Order order;

        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            // Nếu chưa có, tạo đơn mới
            order = new Order();
            order.setUserId(userId);
            order.setStatus("pending");
            order.setTotal(BigDecimal.ZERO); // Ban đầu total là 0
            orderRepository.save(order);
        }

        // 2. Lấy thông tin sản phẩm
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 3. Kiểm tra xem sản phẩm đã có trong giỏ chưa
        Optional<OrderItem> optionalItem = orderItemRepository.findByOrderIdAndProductId(order.getId(), request.getProductId());

        if (optionalItem.isPresent()) {
            // Nếu đã có thì tăng số lượng
            OrderItem item = optionalItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            orderItemRepository.save(item);
        } else {
            // Nếu chưa có thì thêm mới
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            item.setPrice(product.getPrice());
            orderItemRepository.save(item);
        }

        // 4. Cập nhật lại total
        BigDecimal currentTotal = orderItemRepository.calculateTotalByOrderId(order.getId());
        order.setTotal(currentTotal);
        orderRepository.save(order);
    }


    @Override
    public void updateCart(Long userId, CartUpdateRequestDTO request) {
        Order order = orderRepository.findByUserIdAndStatus(userId, "pending")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        OrderItem item = orderItemRepository.findByOrderIdAndProductId(order.getId(), request.getProductId())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trong giỏ"));

        if (request.getQuantity() <= 0) {
            orderItemRepository.delete(item);
        } else {
            item.setQuantity(request.getQuantity());

            // ✅ Xử lý giá trị null của note
            String receivedNote = request.getNote();
            if (receivedNote == null) {
                receivedNote = "";  // Nếu không có ghi chú, đặt giá trị mặc định là chuỗi rỗng
            }

            // ✅ Log kiểm tra xem backend có nhận được note hay không
            System.out.println("📥 Note nhận được từ API: '" + receivedNote + "'");

            item.setNote(receivedNote);
            System.out.println("📥 Note đã được gán vào OrderItem: '" + item.getNote() + "'");

            // 🔥 Lưu vào database
            orderItemRepository.save(item);
            System.out.println("✅ OrderItem đã được lưu thành công!");
        }

        // ✅ Cập nhật lại tổng giá của đơn hàng
        BigDecimal newTotal = orderItemRepository.calculateTotalByOrderId(order.getId());
        order.setTotal(newTotal != null ? newTotal : BigDecimal.ZERO);
        orderRepository.save(order);
    }



    @Override
    public void removeItem(Long userId, Long productId) {
        // 1. Tìm đơn hàng 'pending' của user
        Order order = orderRepository.findByUserIdAndStatus(userId, "pending")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        // 2. Tìm item trong giỏ
        OrderItem item = orderItemRepository.findByOrderIdAndProductId(order.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trong giỏ"));

        // 3. Xóa item
        orderItemRepository.delete(item);

        // 4. Cập nhật lại total
        BigDecimal newTotal = orderItemRepository.calculateTotalByOrderId(order.getId());
        order.setTotal(newTotal != null ? newTotal : BigDecimal.ZERO);
        orderRepository.save(order);
    }

    @Override
    public CartResponseDTO getCart(Long userId) {
        // Lấy order có status "pending" cho user
        Order order = orderRepository.findByUserIdAndStatus(userId, "pending")
                .orElseThrow(() -> new RuntimeException("Giỏ hàng trống"));

        // Lấy tất cả các OrderItem từ order
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        // Chuyển các OrderItem thành CartItemResponseDTO
        List<CartItemResponseDTO> itemDTOs = items.stream().map(item -> {
            Product product = item.getProduct();
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            // ✅ Thay đổi: Nếu item.getNote() là null, thì trả về chuỗi rỗng ""
            String note = item.getNote() != null ? item.getNote() : "";

            return new CartItemResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getMainImg(),
                    product.getPrice(),
                    item.getQuantity(),
                    subtotal,
                    note // ✅ Gửi note không bao giờ là null
            );
        }).toList();

        // Trả về danh sách các món trong giỏ hàng và tổng giá
        return new CartResponseDTO(itemDTOs, order.getTotal());
    }

    @Override
    @Transactional
    public void checkout(Long userId, CheckoutRequestDTO request) {
        // 1. Tìm đơn hàng đang chờ (pending)
        Order order = orderRepository.findByUserIdAndStatus(userId, "pending")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng đang chờ thanh toán."));

        // 2. Cập nhật thông tin đơn hàng từ request
        order.setAddress(request.getAddress());
        order.setAddressNote(request.getAddressNote());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setVoucherCode(request.getVoucherCode());

        // 3. Lấy tất cả OrderItem thuộc về Order này
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

        // 4. Cập nhật note cho từng món trong Order
        for (OrderItem orderItem : orderItems) {
            // Tìm `note` tương ứng từ request
            for (CheckoutItemDTO itemDTO : request.getItems()) {
                if (orderItem.getProduct().getId().equals(itemDTO.getProductId())) {
                    orderItem.setNote(itemDTO.getNote()); // ✅ Cập nhật note
                    orderItemRepository.save(orderItem); // ✅ Lưu thay đổi vào database
                    System.out.println("✅ Đã lưu note cho sản phẩm: " + orderItem.getProduct().getName() + " - Note: " + orderItem.getNote());
                    break;
                }
            }
        }

        // 5. Cập nhật trạng thái và thời gian đặt
        order.setStatus("paid");
        order.setCreatedAt(new Timestamp(System.currentTimeMillis())); // cập nhật thời gian thanh toán

        // 6. Lưu lại đơn hàng
        orderRepository.save(order);
    }



}
