package com.example.atc.domain.pointRecord.service;

import com.example.atc.domain.pointRecord.entity.PointRecordEntity;
import com.example.atc.domain.pointRecord.repository.PointRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointRecordService {

    @Autowired
    private PointRecordRepository pointRecordRepository;

    public List<PointRecordEntity> getAllPointRecords() {
        return pointRecordRepository.findAll();
    }

    public PointRecordEntity getPointRecordById(Long id) {
        return pointRecordRepository.findById(id).orElse(null);
    }

    public PointRecordEntity savePointRecord(PointRecordEntity pointRecord) {
        return pointRecordRepository.save(pointRecord);
    }

    public void deletePointRecord(Long id) {
        pointRecordRepository.deleteById(id);
    }
}
