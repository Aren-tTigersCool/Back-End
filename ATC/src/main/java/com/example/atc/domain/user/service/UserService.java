package com.example.atc.domain.user.service;

import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.entity.ProfilePicture;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.ProfilePictureRepository;
import com.example.atc.domain.user.repository.UserRepository;
import com.example.atc.global.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfilePictureRepository profilePictureRepository;
    private final S3UploadService s3UploadService;

    public ResponseEntity<?> createUser(UserDTO userDTO, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("업로드 할 이미지가 존재하지 않습니다.");
        }
        String fileUrl;
        try {
            fileUrl = savePicture(file);
            ProfilePicture profilePicture = new ProfilePicture();
            profilePicture.setPictureUrl(fileUrl);

            User user = new User();
            User changedUser = this.setEntityData(userDTO, user);
            changedUser.setProfilePicture(profilePicture);
            profilePicture.setUser(changedUser);

            profilePictureRepository.save(profilePicture);
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(changedUser));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 문제로 S3 이미지 업로드에 실패하였습니다.");
        }
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

    public ResponseEntity<?> updateUser(Long userId, UserDTO userDTO, MultipartFile file) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            User changedUser = this.setEntityData(userDTO, user);

            ProfilePicture profilePicture = user.getProfilePicture() != null ? user.getProfilePicture() : new ProfilePicture();
            if (file != null && !file.isEmpty()) {
                try {
                    String fileUrl = savePicture(file);
                    profilePicture.setPictureUrl(fileUrl);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 문제로 S3 이미지 업로드에 실패하였습니다.");
                }
            }
            profilePicture.setUser(changedUser);
            changedUser.setProfilePicture(profilePicture);

            profilePictureRepository.save(profilePicture);
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(changedUser));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id " + userId);
        }
    }

    public ResponseEntity<?> deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            ProfilePicture profilePicture = userOptional.get().getProfilePicture();
            if (profilePicture != null) {
                profilePictureRepository.delete(profilePicture);
            }
            userRepository.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id " + userId);
    }

    private User setEntityData(UserDTO userDTO, User user) {
        user.setUserId(userDTO.getUserId());  // 카카오 ID를 그대로 사용
        user.setUserPw(userDTO.getUserPw());
        user.setCategoryId(userDTO.getCategoryId());
        user.setNickName(userDTO.getNickname()); //초기 닉네임은 kakao닉네임이지만 변경 가능??
        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setCalSum(userDTO.getCalSum());
        user.setCarSum(userDTO.getCarSum());
        user.setTotalPoint(userDTO.getTotalPoint());
        user.setEmail(user.getEmail());
//        user.setEmail(userDTO.getKakaoAccount().getEmail()); //이메일은 변경하면 안 되는디...
        return user;
    }

    private String savePicture(MultipartFile file) throws IOException {
        return s3UploadService.saveFile(file);
    }
}
