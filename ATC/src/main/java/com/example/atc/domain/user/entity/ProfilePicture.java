package com.example.atc.domain.user.entity;

import com.example.atc.domain.plogging.entity.Plogging;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pictureId;
    private String pictureUrl;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", unique = true)
    @JsonManagedReference
    private User user;
}
