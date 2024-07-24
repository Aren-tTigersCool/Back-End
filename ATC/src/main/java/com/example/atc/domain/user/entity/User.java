package com.example.atc.domain.user.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private Long userPw;
    private Long categoryId;
    private Long profilePicId;
    private String nickName;
    private Double height;
    private Double weight;
    private Double calSum;
    private Double carSum;
    private int totalPoint;
}
