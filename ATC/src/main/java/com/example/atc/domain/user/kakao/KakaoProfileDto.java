package com.example.atc.domain.user.kakao;
import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoProfileDto {

    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    public static class KakaoAccount {
        private String email;
        private Profile profile;

        @Data
        public static class Profile{
            @JsonProperty("nickname")
            private String nickname;

        }
    }

//    public UserDTO toUser(){
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId(this.id);
//        userDTO.setEmail(kakaoAccount.getEmail());
//        userDTO.setNickName(kakaoAccount.getProfile().nickname);
//        return userDTO;
//    }



}