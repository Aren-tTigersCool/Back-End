package com.example.atc.domain.plogging.service;

import com.example.atc.domain.plogging.dto.PloggingDto;
import com.example.atc.domain.plogging.entity.Plogging;
import com.example.atc.domain.plogging.entity.PloggingPicture;
import com.example.atc.domain.plogging.repository.PloggingPictureRepository;
import com.example.atc.domain.plogging.repository.PloggingRepository;
import com.example.atc.global.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PloggingService {
    //    @Autowired
    private final PloggingRepository ploggingRepository;

    //    @Autowired
    private final PloggingPictureRepository ploggingPictureRepository;


    //    @Autowired
    private final S3UploadService s3UploadService;


    public List<Plogging> retrieveAllPloggings() {
        return ploggingRepository.findAll();
    }


//    public ResponseEntity<?> createPlogging(PloggingDto dto, MultipartFile file) {
//        if(file == null || file.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("업로드 할 이미지가 존재하지 않습니다.");
//        }
//        String fileUrl = null;
//        try {
//            fileUrl = savePicture(file);
//            PloggingPicture ploggingPicture = new PloggingPicture();
//            ploggingPicture.setPictureUrl(fileUrl);
//
//            Plogging plogging = Plogging.builder()
//                    .timeTaken(dto.getTimeTaken())
//                    .authenticationTime(dto.getAuthenticationTime())
//                    .distance(dto.getDistance())
//                    .location(dto.getLocation())
//                    .ploggingPicture(ploggingPicture)
//                    .build();
//
//            ploggingPicture.setPlogging(plogging);
//            ploggingPictureRepository.save(ploggingPicture);
//            return ResponseEntity.ok().body(ploggingRepository.save(plogging));
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 문제로 S3 이미지 업로드에 실패하였습니다.");
//        }
//    }

    public void updatePlogging(Long recordId, Plogging ploggingDetails) {
        Plogging plogging = ploggingRepository.findById(recordId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + recordId));
        plogging.setTimeTaken(ploggingDetails.getTimeTaken());
        plogging.setDistance(ploggingDetails.getDistance());
        plogging.setAuthenticationTime(ploggingDetails.getAuthenticationTime());
        plogging.setLocation(ploggingDetails.getLocation());
    }

    public void deletePlogging(Long recordId) {
        ploggingRepository.deleteById(recordId);
    }

    public PloggingPicture savePicture(MultipartFile file) throws IOException {
        String pictureUrl = s3UploadService.saveFile(file);
        PloggingPicture ploggingPicture = new PloggingPicture();
        ploggingPicture.setPictureUrl(pictureUrl);
        ploggingPicture.setRecordDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return ploggingPictureRepository.save(ploggingPicture);

//    public String savePicture(MultipartFile file) throws IOException {
//        return s3UploadService.saveFile(file);
//    }
    }
}
