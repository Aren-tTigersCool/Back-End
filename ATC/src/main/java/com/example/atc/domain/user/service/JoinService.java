package com.example.atc.domain.user.service;

import com.example.atc.domain.user.dto.signUpDTO;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void joinProcess(signUpDTO signUpDTO) {

        String username = signUpDTO.getMemberId();
        String password = signUpDTO.getPassword();
        String name = signUpDTO.getName();

        // 이미 해당 ID가 존재하는지 확인
        Boolean isExist = userRepository.existsByMemberId(username);

        // 이미 존재하는 경우 예외 던지기
        if (isExist) {
            throw new IllegalStateException("이미 존재하는 ID입니다.");
        }

        // 새로운 사용자 생성
        User data = new User();
        data.setMemberId(username);
        data.setUserPw(bCryptPasswordEncoder.encode(password));
        data.setNickName(name);
        data.setRole("ROLE_USER");

        // DB에 저장
        userRepository.save(data);
    }
}
