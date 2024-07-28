package com.example.atc.domain.plogging.controller;

import com.example.atc.domain.plogging.dto.PloggingDto;
import com.example.atc.domain.plogging.entity.Plogging;
import com.example.atc.domain.plogging.entity.PloggingPicture;
import com.example.atc.domain.plogging.service.PloggingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/plogging")
public class PloggingController {
    @Autowired
    private PloggingService ploggingService;

    @Operation(summary = "플로깅 인증 내역 생성", description = "플로깅 인증 내역을 생성합니다.")
    @PostMapping
    public ResponseEntity<?> createPlogging(@RequestPart(value = "request") PloggingDto dto, @RequestPart(value = "file") MultipartFile file) {
        return ploggingService.createPlogging(dto, file);
    }

    @Operation(summary = "플로깅 내역 조회", description = "전체 플로깅 내역을 조회합니다.")
    @GetMapping
    public List<Plogging> getAllPloggings() {
        return ploggingService.retrieveAllPloggings();
    }

    @Operation(summary = "게시물 수정", description = "게시물을 수정합니다.")
    @PutMapping("/{recordId}")
    public void updatePlogging(@PathVariable Long recordId, @RequestBody Plogging ploggingDetails) {
        ploggingService.updatePlogging(recordId, ploggingDetails);
    }

    @Operation(summary = "플로깅 인증 내역 삭제", description = "recordID를 통해 게시물을 삭제합니다.")
    @DeleteMapping("/{recodrId}")
    public void deletePlogging(@PathVariable Long recodrId) {
        ploggingService.deletePlogging(recodrId);
    }

//    @Operation(summary = "플로깅 사진 업로드", description = "플로깅 사진을 업로드합니다.")
//    @PostMapping("/upload")
//    public PloggingPicture uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            return ploggingService.savePicture(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.");
//        }
//    }

}

