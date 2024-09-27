package com.example.atc.domain.order.entity;

import com.example.atc.domain.product.entity.Product;
import com.example.atc.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Long price;
    private String status;
    private String paymentId;
    private String cardApprovalNumber; // 카드 승인번호임
    private String installmentInfo; // 할부 정보임
    private String paymentTime;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonIgnore
    private User user;
}
