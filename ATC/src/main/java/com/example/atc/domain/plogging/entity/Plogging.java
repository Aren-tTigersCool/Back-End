package com.example.atc.domain.plogging.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Plogging {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long recordId;
        private int timeTaken;
        private int authenticationTime;
        private String location;
        private int distance;
        @OneToOne
        @JoinColumn(name = "picture_id",unique = true)
        private PloggingPicture ploggingPicture;
}
