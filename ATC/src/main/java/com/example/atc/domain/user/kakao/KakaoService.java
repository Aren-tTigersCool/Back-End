package com.example.atc.domain.user.kakao;

import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.entity.ProfilePicture;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.ProfilePictureRepository;
import com.example.atc.domain.user.repository.UserRepository;
import com.example.atc.global.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private UserDTO userDTO;

    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final UserRepository userRepository;
    private final ProfilePictureRepository profilePictureRepository;

    private String contentType = "Content_Type: application/x-www-form-urlencoded";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirect;

    public String getKakaoToken(String code){
//        System.out.println("code: " + code);
//        System.out.println("client_id: " + clientId);
//        System.out.println("client_secret: " + clientSecret);
//        System.out.println("redirect_uri: " + redirect);
        KakaoTokenDto.Request kakaoTokenDtoRequest =
                KakaoTokenDto.Request.builder()
                        .grant_type("authorization_code")
                        .client_id(clientId)
                        .client_secret(clientSecret)
                        .redirect_uri(redirect)
                        .code(code)
                        .build();

        KakaoTokenDto.Response resDto = kakaoTokenClient.getKakaoToken(contentType,kakaoTokenDtoRequest);
        String token = resDto.getAccess_token();
        System.out.println(token);

        /*
        {
        "id":3638567622,
        "connected_at":"2024-07-26T08:30:56Z",
        "properties":{"nickname":"김다연"},
        "kakao_account":{
            "profile_nickname_needs_agreement":false,
            "profile":{"nickname":"김다연",
            "is_default_nickname":false},"has_email":true,"email_needs_agreement":false,"is_email_valid":true,"is_email_verified":true,
            "email":"day020908@gmail.com"}}

         */
        return token;
    }

    public UserDTO getKakaoInfo(String token){
        userDTO = kakaoInfoClient.getKakaoInfo("Bearer "+token);
        System.out.println(userDTO);
        return userDTO;
    }
    private final S3UploadService s3UploadService;
    public User registerKakaoUser(UserDTO userDTO) {
        String nickname = userDTO.getKakaoAccount().getProfile().getNickname();
        String email = userDTO.getKakaoAccount().getEmail();
        Long kakaoId = userDTO.getUserId();
        String imageUrl = userDTO.getKakaoAccount().getProfile().getProfileImageUrl();
        System.out.println(nickname +"\n"+ email+"\n" + nickname+"\n" + imageUrl);

        User kakaoUser = userRepository.findById(kakaoId) // 있으면 kakaoUser 없으면 null
                .orElse(null);

        ProfilePicture kakaoProfilePicture;

        if (kakaoUser != null) { // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            // 기존 프로필 사진 객체를 재사용
            kakaoProfilePicture = kakaoUser.getProfilePicture() != null ? kakaoUser.getProfilePicture() : new ProfilePicture();
            kakaoUser.setUserId(kakaoId);
            kakaoUser.setEmail(email);
            //닉네임도 변경 가능 여부 (카카오랑 다르게)에 따라 이부분 바뀜
            kakaoUser.setNickName(nickname);

            System.out.println(nickname + "님 로그인");
        } else {
            kakaoProfilePicture = new ProfilePicture();

            kakaoUser = User.builder()
                    .userId(kakaoId)
                    .nickName(nickname)
                    .email(email)
                    .profilePicture(kakaoProfilePicture)
                    .build();

            System.out.println(nickname + "님 회원가입");
        }

        kakaoProfilePicture.setPictureUrl(imageUrl); //카카오프사랑 늘 같은 프사일 때
        kakaoProfilePicture.setUser(kakaoUser);

        profilePictureRepository.save(kakaoProfilePicture);
        userRepository.save(kakaoUser); // 변경사항을 저장

        return kakaoUser;
    }




}