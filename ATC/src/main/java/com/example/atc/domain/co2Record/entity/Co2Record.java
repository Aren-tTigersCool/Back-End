package com.example.atc.domain.co2Record.entity;

import com.example.atc.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Co2Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference
    private User user;

    private double addSubCo2;
    private double totalCo2;
    private double todayTotalCo2;

    private String usedDate;
}