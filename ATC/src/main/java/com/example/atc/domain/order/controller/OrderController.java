package com.example.atc.domain.order.controller;

import com.example.atc.domain.order.dto.OrderRequestDto;
import com.example.atc.domain.order.entity.Orders;
import com.example.atc.domain.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Orders> createOrder(@RequestBody OrderRequestDto orderRequest) {
        Orders newOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(newOrder);
    }

    @PostMapping("/{orderId}/complete")
    public ResponseEntity<Orders> completeOrder(@PathVariable Long orderId, @RequestBody Map<String, String> paymentInfo) {
        String paymentId = paymentInfo.get("paymentId");
        String cardApprovalNumber = paymentInfo.get("cardApprovalNumber");
        String installmentInfo = paymentInfo.get("installmentInfo");

        Orders completedOrder = orderService.completeOrder(orderId, paymentId, cardApprovalNumber, installmentInfo);
        return ResponseEntity.ok(completedOrder);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> webhookData) {
        // 포트원에서 보낸 데이터를 처리
        // 얘는 실결제 연동시 사용가능한거라 일단 테스트 용으로 만들었음
        return ResponseEntity.ok("웹훅 처리 완료");
    }

    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.findAllOrders();
    }
}
