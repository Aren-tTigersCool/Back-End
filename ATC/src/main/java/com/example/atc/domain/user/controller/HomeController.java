/* package com.example.atc.domain.user.controller;

import java.util.HashMap;

import com.example.atc.domain.user.KakaoAPI;
import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/kakao")
public class HomeController {

    KakaoAPI kakaoApi = new KakaoAPI();

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // 1번 인증코드 요청 전달
        String accessToken = kakaoApi.getAccessToken(code);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        System.out.println("login info : " + userInfo.toString());

        if (userInfo.get("nickname") != null) {
            session.setAttribute("userId", userInfo.get("nickname"));
            session.setAttribute("accessToken", accessToken);

            mav.addObject("userId", userInfo.get("nickname"));
            mav.setViewName("index");
        }
        return mav;

    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        kakaoApi.kakaoLogout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        mav.setViewName("index");
        return mav;
    }




}
 */
