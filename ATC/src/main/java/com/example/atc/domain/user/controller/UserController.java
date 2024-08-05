package com.example.atc.domain.user.controller;

import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.dto.logInDTO;
import com.example.atc.domain.user.dto.signUpDTO;
import com.example.atc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/check")
    public boolean checkIdAndPw(@RequestBody String memberId) {
        return userService.checkId(memberId);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody signUpDTO signUpDTO) {
        if (userService.checkId(signUpDTO.getMemberId())){
            userService.signUp(signUpDTO);
            return ResponseEntity.ok("회원가입 성공");
        }
        else return ResponseEntity.ok("회원가입 실패");
    }
    @PostMapping ("/login") //로그인
    public ResponseEntity<?> login(@RequestBody logInDTO logInDTO) {
        return userService.login(logInDTO);
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestPart UserDTO userDTO, @RequestPart MultipartFile file) {
        return userService.createUser(userDTO, file);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}") //회원정보 수정
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestPart UserDTO userDTO,
                                        @RequestPart(required = false) MultipartFile file) {
        return userService.updateUser(id, userDTO, file);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
