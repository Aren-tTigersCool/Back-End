package com.example.atc.domain.user.service;

import com.example.atc.domain.user.dto.signUpDTO;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(signUpDTO signUpDTO) {

        String username = signUpDTO.getMemberId();
        String password = signUpDTO.getPassword();

        Boolean isExist = userRepository.existsByMemberId(username);

        if (isExist) {

            return;
        }

        User data = new User();

        data.setMemberId(username);
        data.setUserPw(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}