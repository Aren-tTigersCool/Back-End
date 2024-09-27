package com.example.atc.domain.user.service;

import com.example.atc.domain.user.dto.CustomUserDetails;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.UserRepository;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB에서 조회
        User userData = userRepository.findByMemberId(username);
        System.out.println(username);
//        System.out.println(userData);

        if (userData != null) {
            System.out.println("user is not null");
            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
            return new CustomUserDetails(userData);
        }

        return null;
    }
}