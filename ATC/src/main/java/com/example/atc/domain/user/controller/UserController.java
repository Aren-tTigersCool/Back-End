package com.example.atc.domain.user.controller;

import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.dto.idDTO;
import com.example.atc.domain.user.dto.logInDTO;
import com.example.atc.domain.user.dto.signUpDTO;
import com.example.atc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
