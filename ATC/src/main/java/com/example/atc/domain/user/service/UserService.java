package com.example.atc.domain.user.service;

import com.example.atc.domain.pointRecord.entity.PointRecord;
import com.example.atc.domain.user.dto.UserDTO;
import com.example.atc.domain.user.entity.ProfilePicture;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.ProfilePictureRepository;
import com.example.atc.domain.user.repository.UserRepository;
import com.example.atc.global.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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



    public ResponseEntity<?> login(String memberId, String password) {
        String id = memberId;
        String pw = password;

        System.out.println(id + pw);

        try {
            // 사용자 id/password 일치하는지 확인
            boolean existed = userRepository.existsByMemberIdAndUserPw(id, pw);
            if(!existed) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력하신 정보가 존재하지 않습니다.");
            } else {
                Optional<User> userOptional = userRepository.findByMemberId(id);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    System.out.println("로그인 성공");
                    return ResponseEntity.status(HttpStatus.OK).body(user);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터베이스 연결에 실패했습니다.");
        }
    }

    public boolean checkId(String memberId) {
        return !userRepository.existsByMemberId(memberId);
    }

    public ResponseEntity<?> signUp(String memberId, String name, String password) {
        try {
            if (userRepository.existsByMemberId(memberId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 ID입니다.");
            }

            if (!isValidPassword(String.valueOf(password))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호는 8자 이상, 대문자, 소문자, 숫자 및 특수문자를 포함해야 합니다.");
            }

            else {

                UserDTO userDTO = new UserDTO();
                userDTO.setMemberId(memberId);
                userDTO.setNickname(name);
                userDTO.setUserPw(password);
                User user = new User();
                user.setMemberId(memberId);
                user.setUserPw(password);
                user.setCategoryId(null);
                user.setNickName(name);
                user.setHeight(null);
                user.setWeight(null);
                user.setCalSum(null);
                user.setCarSum(null);
                user.setTotalPoint(0);
                User savedUser = userRepository.save(user);

                return ResponseEntity.status(HttpStatus.OK).body(savedUser);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류가 발생했습니다.");
        }
    }

//    public boolean isAvailable(Long userId, String pw) {
//        return !userRepository.existsByUserIdAndUserPw(userId, pw );
//    }

//    public ResponseEntity<?> signUp(UserDTO userDTO) {
//        try {
//            if (userRepository.existsById(userDTO.getUserId())) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 ID입니다.");
//            }
//
//            String pw = userDTO.getUserPw();
//            if (!isValidPassword(String.valueOf(pw))) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호는 8자 이상, 대문자, 소문자, 숫자 및 특수문자를 포함해야 합니다.");
//            }
//
//            User user = new User();
//            User changedUser = this.setEntityData(userDTO, user);
//
//            User savedUser = userRepository.save(changedUser);
//
//            return ResponseEntity.status(HttpStatus.OK).body(savedUser);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 오류가 발생했습니다.");
//        }
//    }

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
        user.setUserId(userDTO.getUserId());
        user.setUserPw(userDTO.getUserPw());
        user.setCategoryId(userDTO.getCategoryId());
        user.setNickName(userDTO.getNickname());
        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setCalSum(userDTO.getCalSum());
        user.setCarSum(userDTO.getCarSum());
        user.setTotalPoint(userDTO.getTotalPoint());

        return user;
    }

    private String savePicture(MultipartFile file) throws IOException {
        return s3UploadService.saveFile(file);
    }

    public ResponseEntity<User> saveUser(User user){
        user = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}



//    public User registerKakaoUser(UserDTO userDTO) {
//        User kakaoUser = userRepository.findById(userDTO.getUserId())
//                .orElse(null);
//
//        ProfilePicture kakaoProfilePicture;
//
//        if (kakaoUser != null) { // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
//            // 기존 프로필 사진 객체를 재사용
//            kakaoProfilePicture = kakaoUser.getProfilePicture() != null ? kakaoUser.getProfilePicture() : new ProfilePicture();
//            kakaoUser.setUserId(kakaoId);
//            kakaoUser.setEmail(email);
//            //닉네임도 변경 가능 여부 (카카오랑 다르게)에 따라 이부분 바뀜
//            kakaoUser.setNickName(nickname);
//
//            System.out.println(nickname + "님 로그인");
//        } else {
//            kakaoProfilePicture = new ProfilePicture();
//
//            kakaoUser = User.builder()
//                    .userId(kakaoId)
//                    .nickName(nickname)
//                    .email(email)
//                    .profilePicture(kakaoProfilePicture)
//                    .build();
//
//            System.out.println(nickname + "님 회원가입");
//        }
//
//        kakaoProfilePicture.setPictureUrl(imageUrl); //카카오프사랑 늘 같은 프사일 때
//        kakaoProfilePicture.setUser(kakaoUser);
//
//        profilePictureRepository.save(kakaoProfilePicture);
//        userRepository.save(kakaoUser); // 변경사항을 저장
//
//        return kakaoUser;
//    }
