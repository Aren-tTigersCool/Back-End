package com.example.atc.domain.product.service;

import com.example.atc.domain.product.entity.Product;
import com.example.atc.domain.product.repository.ProductRepository;
import com.example.atc.global.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final S3UploadService s3UploadService;

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> findProductTitle() {
        return productRepository.findProductTitle();
    }

    public String findProductBarcode(String productName) {
        return productRepository.findProductBarcode(productName);
    }

    public void decreaseProductQuantity(String productName) {
        Product product = productRepository.findByProductName(productName);

        if (product != null) {
            int currentQuantity = Integer.parseInt(product.getProductQuantity());
            if (currentQuantity > 0) {
                product.setProductQuantity(String.valueOf(currentQuantity - 1));
                productRepository.save(product);
            } else {
                throw new RuntimeException("재고가 부족합니다.");
            }
        } else {
            throw new RuntimeException("제품이 존재하지 않습니다.");
        }
    }
}
