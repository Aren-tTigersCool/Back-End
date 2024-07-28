package com.example.atc.domain.plogging.service;

import com.example.atc.domain.plogging.entity.Plogging;
import com.example.atc.domain.plogging.entity.PloggingPicture;
import com.example.atc.domain.plogging.repository.PloggingPictureRepository;
import com.example.atc.domain.plogging.repository.PloggingRepository;
import com.example.atc.global.S3UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PloggingService {
    @Autowired
    private PloggingRepository ploggingRepository;

    @Autowired
    private PloggingPictureRepository ploggingPictureRepository;


    @Autowired
    private S3UploadService s3UploadService;
    public List<Plogging> retrieveAllPloggings(){return ploggingRepository.findAll();}

    public Plogging createPlogging(Plogging plogging){
        return ploggingRepository.save(plogging);
    }

    public void updatePlogging(Long recordId, Plogging ploggingDetails){
        Plogging plogging = ploggingRepository.findById(recordId).orElseThrow(()->new IllegalArgumentException("Invalid post ID: "+recordId));
        plogging.setTimeTaken(ploggingDetails.getTimeTaken());
        plogging.setDistance(ploggingDetails.getDistance());
        plogging.setAuthenticationTime(ploggingDetails.getAuthenticationTime());
        plogging.setLocation(ploggingDetails.getLocation());
    }

    public void deletePlogging(Long recordId){
        ploggingRepository.deleteById(recordId);
    }


    public PloggingPicture savePicture(MultipartFile file) throws IOException{
        String pictureUrl = s3UploadService.saveFile(file);
        PloggingPicture ploggingPicture = new PloggingPicture();
        ploggingPicture.setPictureUrl(pictureUrl);
        return ploggingPictureRepository.save(ploggingPicture);
    }
}
