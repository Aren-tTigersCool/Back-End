package com.example.atc.domain.plogging.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class PloggingPicture {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pictureId;
    private String pictureUrl;
    @OneToOne(mappedBy = "ploggingPicture", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Plogging plogging;
}