package com.example.atc.domain.order.service;

import com.example.atc.domain.order.dto.OrderDto;
import com.example.atc.domain.order.entity.Orders;
import com.example.atc.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    public List<Orders> findAllOrders(){
        return orderRepository.findAll();
    }

    public List<Orders> findAllOrdersByUser(Long userId) {
        return orderRepository.findAllOrdersByUserId(userId);
    }

}
