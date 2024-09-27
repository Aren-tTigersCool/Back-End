package com.example.atc.domain.order.repository;

import com.example.atc.domain.order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    @Query("SELECT o FROM Orders o WHERE o.userId = :userId")
    List<Orders> findAllOrdersByUserId(@Param("userId") Long userId);
}
}
