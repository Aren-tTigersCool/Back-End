package com.example.atc.domain.product.repository;

import com.example.atc.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p.productBarcodeUrl FROM Product p WHERE p.productName = :productName")
    String findProductBarcode(@Param("productName") String productName);

    @Query("SELECT p FROM Product p WHERE p.productName = :productName")
    Product findByProductName(@Param("productName") String productName);
}
