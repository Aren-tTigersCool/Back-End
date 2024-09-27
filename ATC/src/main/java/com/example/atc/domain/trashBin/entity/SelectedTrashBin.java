package com.example.atc.domain.trashBin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SelectedTrashBin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    public SelectedTrashBin() {
    }

    public SelectedTrashBin(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
