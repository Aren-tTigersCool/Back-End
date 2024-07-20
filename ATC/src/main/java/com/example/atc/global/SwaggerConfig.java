package com.example.atc.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("멋사 게시판 CRUD API")
                        .version("v1.0.0").
                        description("멋사 게시판 CURD 세션에서 만든 POST API입니다.")
                );
    }
}
