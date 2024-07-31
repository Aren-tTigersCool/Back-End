package com.example.atc.domain.user.service;

import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<?> createUser(UserDTO userDTO) {
        User user = new User();

        User changedUser = this.setEntityData(userDTO, user);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(changedUser));
    }

    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    public ResponseEntity<?> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id " + userId);
    }

    public ResponseEntity<?> updateUser(Long userId, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            User changedUser = this.setEntityData(userDTO, user);
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(changedUser));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id " + userId);
        }
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id " + userId);
    }

    private User setEntityData(UserDTO userDTO, User user) {
        user.setUserId(userDTO.getUserId());  // 카카오 ID를 그대로 사용
        user.setUserPw(userDTO.getUserPw());
        user.setCategoryId(userDTO.getCategoryId());
        user.setProfilePicId(userDTO.getProfilePicId());
        user.setNickName(userDTO.getKakaoAccount().getProfile().getNickname());
        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setCalSum(userDTO.getCalSum());
        user.setCarSum(userDTO.getCarSum());
        user.setTotalPoint(userDTO.getTotalPoint());
        user.setEmail(userDTO.getKakaoAccount().getEmail());
        return user;
    }
}
