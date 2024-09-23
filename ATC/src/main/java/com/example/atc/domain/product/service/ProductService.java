package com.example.atc.domain.product.service;

import com.example.atc.domain.product.dto.ProductDto;
import com.example.atc.domain.product.entity.Product;
import com.example.atc.domain.product.repository.ProductRepository;
import com.example.atc.global.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public String uploadToS3(MultipartFile productPicture) throws IOException {
        return s3UploadService.saveFile(productPicture);
    }

    public Product createProduct(ProductDto productDto, MultipartFile productPicture, MultipartFile productBarcode) throws IOException {
        // S3에 파일 업로드 및 URL 저장
        String productPictureUrl = uploadToS3(productPicture);
        String productBarcodeUrl = uploadToS3(productBarcode);

        // Product 객체 생성 및 DTO로부터 값 설정
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setProductQuantity(productDto.getProductQuantity());
        product.setProductPrice(productDto.getProductPrice());
        product.setCategory(productDto.getCategory());

        // S3에 업로드된 파일의 URL 설정
        product.setProductPictureUrl(productPictureUrl);
        product.setProductBarcodeUrl(productBarcodeUrl);

        // 데이터베이스에 상품 저장
        return productRepository.save(product);
    }
}
