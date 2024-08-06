package com.example.atc.domain.calorieRecord.entity;

import com.example.atc.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CalorieRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private User user;

    private double addSubCalorie;
    private double totalCalorie;
    private double todayTotalCalorie;

    private String usedDate;
}