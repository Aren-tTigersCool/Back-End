package com.example.atc.domain.plogging.service;

import com.example.atc.domain.plogging.entity.PloggingRepo;
import com.example.atc.domain.plogging.repository.PloggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PloggingService {
    @Autowired
    private PloggingRepository ploggingRepository;

    public List<PloggingRepo> retrieveAllPloggings(){return ploggingRepository.findAll();}

    public PloggingRepo createPlogging(PloggingRepo ploggingRepo){
        return ploggingRepository.save(ploggingRepo);
    }

    public void upadatePlogging(Long recordId, PloggingRepo ploggingDetails){
        PloggingRepo plogging = ploggingRepository.findById(recordId).orElseThrow(()->new IllegalArgumentException("Invalid post ID: "+recordId));
        plogging.setTimeTaken(ploggingDetails.getTimeTaken());
        plogging.setDistance(ploggingDetails.getDistance());
        plogging.setAuthenticationTime(ploggingDetails.getAuthenticationTime());
        plogging.setLocation(ploggingDetails.getLocation());
    }

    public void deletePlogging(Long recordId){
        ploggingRepository.deleteById(recordId);
    }
}
