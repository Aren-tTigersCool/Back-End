package com.example.atc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test API", description = "아무것도 없음")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class TestController {

    @Operation(summary = "프로젝트 수정 API")
    @GetMapping("/test")
    public String greeting() {
        return "Hello World!";
    }
}
