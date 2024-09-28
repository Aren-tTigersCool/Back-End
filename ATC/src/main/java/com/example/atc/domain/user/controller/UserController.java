package com.example.atc.domain.user.controller;

import com.example.atc.domain.user.dto.*;
import com.example.atc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/check")
    public boolean checkIdAndPw(@RequestBody idDTO idDTO) {
        return userService.checkId(idDTO.getMemberId());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestPart UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getUserByMemberId(@PathVariable String memberId) {
        return userService.getUserByMemberId(memberId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestPart UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyPage(Authentication authentication) {
        // 인증된 사용자의 정보를 가져옴
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return userService.getUserByMemberId(userDetails.getUsername());
    }

}
