package com.example.atc.domain.product.entity;

import com.example.atc.domain.order.entity.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private String productQuantity;
    private String productPictureUrl;
    private String productBarcodeUrl;
    private String category;
    private int productPrice;

}
