package com.example.atc.domain.product.controller;

import com.example.atc.domain.product.dto.ProductDto;
import com.example.atc.domain.product.entity.Product;
import com.example.atc.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Operation(summary = "상품 바코드 조회", description = "해당 상품의 바코드를 조회합니다.")
    @PostMapping("/barcode")
    public String getProductBarcode(@RequestParam String productName) {
        String barcodeUrl = productService.findProductBarcode(productName);
        productService.decreaseProductQuantity(productName);
        return barcodeUrl;
    }

    @Operation(summary = "새 상품 등록", description = "새로운 상품을 등록합니다.")
    @PostMapping("/create")
    public Product createProduct(
            @RequestPart(value = "productData") ProductDto productDto,  // DTO로 데이터 받음
            @RequestPart(value = "productPicture") MultipartFile productPicture,
            @RequestPart(value = "productBarcode") MultipartFile productBarcode
    ) throws IOException {
        // 컨트롤러에서는 데이터를 받아 서비스로 전달
        return productService.createProduct(productDto, productPicture, productBarcode);
    }
}
