package com.example.atc.domain.user.kakao;

import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;
    @GetMapping("/login/oauth/kakao")
    public String kakaoCallback(String code){
        System.out.println("code = " + code);
        String token = kakaoService.getKakaoToken(code);
        UserDTO userDTO = kakaoService.getKakaoInfo(token);
        User user = kakaoService.registerKakaoUser(userDTO);

        return token;
    }



}
