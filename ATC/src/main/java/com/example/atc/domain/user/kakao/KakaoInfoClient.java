package com.example.atc.domain.user.kakao;

import com.example.atc.domain.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoinfo", url ="https://kapi.kakao.com")
public interface KakaoInfoClient {

    @PostMapping(value = "/v2/user/me",consumes = "application/json")
    UserDTO getKakaoInfo(
            @RequestHeader("Authorization") String accessToken);

}