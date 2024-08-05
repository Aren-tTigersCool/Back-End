package com.example.atc.domain.pointRecord.entity;

import com.example.atc.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PointRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private User user;

    private int addSubPoint;
    private int totalPoint;
    private int todayTotalPoint;

    private String usedDate;
}