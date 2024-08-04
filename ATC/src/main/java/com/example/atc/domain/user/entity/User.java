package com.example.atc.domain.user.entity;

import com.example.atc.domain.plogging.entity.PloggingPicture;
import com.example.atc.domain.pointRecord.entity.PointRecord;
import com.example.atc.domain.user.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private Long userId;  // 카카오 ID를 그대로 사용

    private Long userPw;
    private Long categoryId;
    private String nickName;
    private Double height;
    private Double weight;
    private Double calSum;
    private Double carSum;
    private int totalPoint;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PointRecord> pointRecords = new LinkedList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private ProfilePicture profilePicture;

}
