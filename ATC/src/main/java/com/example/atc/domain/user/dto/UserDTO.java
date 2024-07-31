package com.example.atc.domain.user.dto;

import com.example.atc.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {

    @JsonProperty("id")
    private Long userId;


    private Long userPw, categoryId, profilePicId;
    private Double height, weight, calSum, carSum;
    private int totalPoint;

    @JsonProperty("kakao_account")
    private UserDTO.KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {
        private String email;
        private UserDTO.KakaoAccount.Profile profile;

        @Data
        public static class Profile{
            @JsonProperty("nickname")
            private String nickname;

        }
    }
}
