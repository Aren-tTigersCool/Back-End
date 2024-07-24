package com.example.atc.domain.plogging.controller;

import com.example.atc.domain.plogging.entity.PloggingRepo;
import com.example.atc.domain.plogging.service.PloggingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class SpringController {
    @Autowired
    private PloggingService ploggingService;

    @Operation(summary = "플로깅 내역 조회", description = "전체 플로깅 내역을 조회합니다.")
    @GetMapping
    public List<PloggingRepo> getAllPosts() {
        return ploggingService.retrieveAllPloggings();
    }

    @Operation(summary = "플로깅 인증 내역 생성", description = "플로깅 인증 내역을 생성합니다.")
    @PostMapping
    public PloggingRepo createPost(@RequestBody PloggingRepo ploggingRepo) {
        return ploggingService.createPlogging(ploggingRepo);
    }

    @Operation(summary = "게시물 수정", description = "게시물을 수정합니다.")
    @PutMapping("/{recordId}")
    public void updatePost(@PathVariable Long recordId, @RequestBody PloggingRepo ploggingDetails) {
        ploggingService.upadatePlogging(recordId, ploggingDetails);
    }

    @Operation(summary = "플로깅 인증 내역 삭제", description = "recordID를 통해 게시물을 삭제합니다.")
    @DeleteMapping("/{recodrId}")
    public void deletePlogging(@PathVariable Long recodrId) {
        ploggingService.deletePlogging(recodrId);
    }
}