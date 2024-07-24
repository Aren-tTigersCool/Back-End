package com.example.atc.domain.plogging.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class PloggingRepo {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long recordId;
        private int timeTaken;
        private int authenticationTime;
        private String location;
        private int distance;

}
