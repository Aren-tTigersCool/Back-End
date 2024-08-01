package com.example.atc.domain.user.dto;

import com.example.atc.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {

    @JsonProperty("id")
    private Long userId;


    private Long userPw, categoryId;
    private Double height, weight, calSum, carSum;
    private int totalPoint;
    private String nickname;

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

            @JsonProperty("profile_image_url")
            public String profileImageUrl;

            @JsonProperty("thumbnail_image_url")
            public String thumbnailImageUrl;
        }
    }
}
