package com.example.atc.domain.user.entity;

import com.example.atc.domain.pointRecord.entity.PointRecord;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PointRecord> pointRecords = new LinkedList<>();
}
