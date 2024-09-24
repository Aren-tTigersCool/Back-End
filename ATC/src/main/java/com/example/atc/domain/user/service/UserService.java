package com.example.atc.domain.user.service;

import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.dto.logInDTO;
import com.example.atc.domain.user.dto.signUpDTO;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.ProfilePictureRepository;
import com.example.atc.domain.user.repository.UserRepository;
import com.example.atc.global.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfilePictureRepository profilePictureRepository;
    private final S3UploadService s3UploadService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<?> login(logInDTO logInDTO) {
        String id = logInDTO.getMemberId();
        String pw = logInDTO.getPassword();

        System.out.println(id + pw);

        try {
            // 사용자 id로 유저 정보 찾기
            User userOptional = userRepository.findByMemberId(id);

            // 사용자가 존재하는지 확인
            if (userOptional!=null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
            }

            User user = userOptional;

            // 입력된 비밀번호가 데이터베이스에 저장된 암호화된 비밀번호와 일치하는지 확인
            if (!bCryptPasswordEncoder.matches(pw, user.getUserPw())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect Password");
            }

            // 로그인 성공 시 유저 정보 반환
            return ResponseEntity.status(HttpStatus.OK).body(user);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DB Error");
        }
    }

    public boolean checkId(String memberId) {
        return userRepository.existsByMemberId(memberId);
    }

    public ResponseEntity<?> signUp(signUpDTO signUpDTO) {
        String memberId = signUpDTO.getMemberId();
        String password = signUpDTO.getPassword();
        String name = signUpDTO.getName();
        try {
            if (userRepository.existsByMemberId(memberId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Already Exist");
            }

            if (!isValidPassword(String.valueOf(password))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PW Error: 비밀번호는 8자 이상, 대문자, 소문자, 숫자 및 특수문자를 포함해야 합니다.");
            }

            else {

                UserDTO userDTO = new UserDTO();
                userDTO.setMemberId(memberId);
                userDTO.setNickname(name);
                userDTO.setUserPw(bCryptPasswordEncoder.encode(password));
                User user = new User();
                user.setMemberId(memberId);
                user.setUserPw(bCryptPasswordEncoder.encode(password));
                user.setCategoryId(null);
                user.setNickName(name);
                user.setHeight(null);
                user.setWeight(null);
//                user.setCalSum(null);
//                user.setCarSum(null);
                user.setTotalPoint(0);

                user.setTotalCo2(0.0);
                user.setTotalCalorie(0.0);

                user.setRole("ROLE_ADMIN");

                User savedUser = userRepository.save(user);

                return ResponseEntity.status(HttpStatus.OK).body(savedUser);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류가 발생했습니다.");
        }
    }

    // 비밀번호 검증
    private boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        // 정규표현식 패턴: 8자 이상, 대문자, 소문자, 숫자, 특수문자 포함
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }


    public ResponseEntity<?> createUser(UserDTO userDTO) {
        try {
            User user = new User();
            User changedUser = this.setEntityData(userDTO, user);
            return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(changedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 연결에 실패하였습니다.");
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

    public ResponseEntity<?> getUserByMemberId(String memberId){
        User user = userRepository.findByMemberId(memberId);
        if (user!=null)
            return ResponseEntity.status(HttpStatus.OK).body(user);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id " + memberId);
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
        user.setUserId(userDTO.getUserId());
        user.setUserPw(userDTO.getUserPw());
        user.setCategoryId(userDTO.getCategoryId());
        user.setNickName(userDTO.getNickname());
        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        //user.setCalSum(userDTO.getCalSum());
        //user.setCarSum(userDTO.getCarSum());
        user.setTotalPoint(userDTO.getTotalPoint());
        user.setTotalCo2(userDTO.getTotalCo2());
        user.setTotalCalorie(userDTO.getTotalCalorie());
        return user;
    }


    public ResponseEntity<User> saveUser(User user){
        user = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
