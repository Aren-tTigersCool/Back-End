package com.example.atc.domain.pointRecord.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class PointRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRecordId;

//    @ManyToOne
//    @JoinColumn(name = "userId", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "profilePicId", nullable = false)
//    private ProfilePicture profilePicture;

    private int addSubPoint;

    @Temporal(TemporalType.TIMESTAMP)
    private Date usedDate;
}
