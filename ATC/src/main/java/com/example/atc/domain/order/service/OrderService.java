package com.example.atc.domain.order.service;

import com.example.atc.domain.order.dto.OrderRequestDto;
import com.example.atc.domain.order.entity.Orders;
import com.example.atc.domain.order.repository.OrderRepository;
import com.example.atc.domain.product.entity.Product;
import com.example.atc.domain.product.repository.ProductRepository;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Orders createOrder(OrderRequestDto orderRequest) {
        Product product = productRepository.findById(orderRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Orders order = new Orders();
        order.setPrice(orderRequest.getPrice());
        order.setProduct(product);
        order.setUser(user);
        order.setStatus("결제 준비");
        return orderRepository.save(order);
    }

    public Orders completeOrder(Long orderId, String paymentId, String cardApprovalNumber, String installmentInfo) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        order.setPaymentId(paymentId);
        order.setCardApprovalNumber(cardApprovalNumber);
        order.setInstallmentInfo(installmentInfo);
        order.setStatus("결제 완료");
        order.setPaymentTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return orderRepository.save(order);
    }

    public List<Orders> findAllOrders(){
        return orderRepository.findAll();
    }

    public List<Orders> findAllOrdersByUser(Long userId) {
        return orderRepository.findAllOrdersByUserId(userId);
    }

}
