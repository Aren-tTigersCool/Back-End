package com.example.atc.domain.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
//admin 권한 부여 테스트
public class AdminController {
    @GetMapping("/admin")
    public String adminP() {

        return "admin Controller";
    }
}
