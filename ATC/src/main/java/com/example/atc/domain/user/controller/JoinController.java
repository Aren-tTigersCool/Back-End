package com.example.atc.domain.user.controller;

import com.example.atc.domain.user.dto.signUpDTO;
import com.example.atc.domain.user.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinProcess(signUpDTO signUpDTO) {

        System.out.println(signUpDTO.getMemberId());
        joinService.joinProcess(signUpDTO);

        return "ok";
    }
}