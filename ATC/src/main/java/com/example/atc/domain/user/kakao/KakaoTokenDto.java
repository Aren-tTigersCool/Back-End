package com.example.atc.domain.user.kakao;

import lombok.Builder;
import lombok.Data;

public class KakaoTokenDto {
    @Data
    @Builder
    public static class Request {
        private String grant_type;
        private String client_secret;
        private String client_id;
        private String redirect_uri;
        private String code;
    }

    @Data
    @Builder
    public static class Response {
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private String scope;
    }
}
