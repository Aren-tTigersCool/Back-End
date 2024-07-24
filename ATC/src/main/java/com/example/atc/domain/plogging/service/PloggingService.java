package com.example.atc.domain.plogging.service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.atc.domain.plogging.entity.Plogging;
import com.example.atc.domain.plogging.repository.PloggingRepository;
import com.example.atc.global.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PloggingService {
    @Autowired
    private PloggingRepository ploggingRepository;

    @Autowired
    private S3UploadService s3UploadService;
    public List<Plogging> retrieveAllPloggings(){return ploggingRepository.findAll();}

    public Plogging createPlogging(Plogging plogging){
        return ploggingRepository.save(plogging);
    }

    public void upadatePlogging(Long recordId, Plogging ploggingDetails){
        Plogging plogging = ploggingRepository.findById(recordId).orElseThrow(()->new IllegalArgumentException("Invalid post ID: "+recordId));
        plogging.setTimeTaken(ploggingDetails.getTimeTaken());
        plogging.setDistance(ploggingDetails.getDistance());
        plogging.setAuthenticationTime(ploggingDetails.getAuthenticationTime());
        plogging.setLocation(ploggingDetails.getLocation());
    }

    public void deletePlogging(Long recordId){
        ploggingRepository.deleteById(recordId);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        return s3UploadService.saveFile(file);
    }
}
