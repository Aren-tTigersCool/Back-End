package com.example.atc.domain.category.entity;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String categoryName;
}