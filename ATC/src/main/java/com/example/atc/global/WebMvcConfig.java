package com.example.atc.global;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 URL 경로에 대해 허용
                .allowedOriginPatterns("*") // 모든 출처 허용
                .allowedMethods("*") // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 인증 정보 허용 (쿠키 등)
                .maxAge(3600); // 요청 캐시 시간
    }
}
