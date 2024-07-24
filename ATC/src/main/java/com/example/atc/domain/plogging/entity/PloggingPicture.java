package com.example.atc.domain.plogging.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PloggingPicture {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pictureId;
    private String pictureUrl;
    @OneToOne(mappedBy = "ploggingPicture")
    private Plogging plogging;
}