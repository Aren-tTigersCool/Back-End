package com.example.atc.domain.user.service;

import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUserPw(userDTO.getUserPw());
        user.setCategoryId(userDTO.getCategoryId());
        user.setProfilePicId(userDTO.getProfilePicId());
        user.setNickName(userDTO.getNickName());
        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setCalSum(userDTO.getCalSum());
        user.setCarSum(userDTO.getCarSum());
        user.setTotalPoint(userDTO.getTotalPoint());
        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUserPw(userDTO.getUserPw());
            user.setCategoryId(userDTO.getCategoryId());
            user.setProfilePicId(userDTO.getProfilePicId());
            user.setNickName(userDTO.getNickName());
            user.setHeight(userDTO.getHeight());
            user.setWeight(userDTO.getWeight());
            user.setCalSum(userDTO.getCalSum());
            user.setCarSum(userDTO.getCarSum());
            user.setTotalPoint(userDTO.getTotalPoint());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id " + userId);
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

