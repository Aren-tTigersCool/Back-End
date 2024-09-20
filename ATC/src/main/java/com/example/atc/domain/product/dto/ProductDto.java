package com.example.atc.domain.product.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String productName;
    private String productQuantity;
    private String productPictureUrl;
    private String productBarcodeUrl;
    private int productPrice;
}
