package com.example.atc.domain.user.dto;

import com.example.atc.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {

    @JsonProperty("id")
    private Long userId;


    private Long  categoryId;
    private Double height, weight, calSum, carSum;
    private int totalPoint, todayTotalPoint;
    private String userPw,nickname;

}
