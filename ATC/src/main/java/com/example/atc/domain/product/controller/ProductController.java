package com.example.atc.domain.product.controller;

import com.example.atc.domain.product.entity.Product;
import com.example.atc.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "전체 상품 설명 조회", description = "상품의 전체 내용을 조회합니다.")
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.findAllProducts();
    }

    @Operation(summary = "상품 간략 조회", description = "상품의 이미지와 이름을 조회합니다.")
    @GetMapping("/title")
    public List<Product> getAllProductTitle(){
        return productService.findProductTitle();
    }

    @Operation(summary = "상품 바코드 조회", description = "해당 상품의 바코드를 조회합니다.")
    @GetMapping("/barcode")
    public String getProductBarcode(@RequestParam String productName) {
        String barcodeUrl = productService.findProductBarcode(productName);

        productService.decreaseProductQuantity(productName);
        return barcodeUrl;
    }

}
