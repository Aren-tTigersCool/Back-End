package com.example.atc.domain.trashBin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class TrashBin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private double latitude;
    @Setter
    private double longitude;

    public TrashBin() {
    }

    public TrashBin(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
