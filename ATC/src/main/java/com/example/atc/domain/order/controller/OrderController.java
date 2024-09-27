package com.example.atc.domain.order.controller;

import com.example.atc.domain.order.dto.OrderDto;
import com.example.atc.domain.order.entity.Orders;
import com.example.atc.domain.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Orders> getAllProducts(){
        return orderService.findAllOrders();
    }

    @GetMapping("/orders/user")
    public List<Orders> findAllOrdersByUser(@RequestParam Long userId) {
        return orderService.findAllOrdersByUser(userId);
    }


}
