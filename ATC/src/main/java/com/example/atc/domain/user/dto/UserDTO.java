package com.example.atc.domain.user.dto;

import lombok.Data;

@Data
public class UserDTO
{
    private Long userId, userPw, categoryId, profilePicId;
    private String nickName;
    private Double height, weight, calSum, carSum;
    private int totalPoint;

}
