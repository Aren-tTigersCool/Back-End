package com.example.atc.domain.plogging.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Plogging {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long recordId;
        private int timeTaken;
        private int authenticationTime;
        private String location;
        private int distance;

        @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "picture_id", unique = true)
        @JsonManagedReference
        private PloggingPicture ploggingPicture;

        @Builder
        public Plogging(int timeTaken, int authenticationTime, String location, int distance, PloggingPicture ploggingPicture) {
                this.timeTaken = timeTaken;
                this.authenticationTime = authenticationTime;
                this.location = location;
                this.distance = distance;
                this.ploggingPicture = ploggingPicture;
        }

        public Plogging() {

        }
}
