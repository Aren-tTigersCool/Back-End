package com.example.atc.domain.pointRecord.service;

import com.example.atc.domain.pointRecord.dto.PointRecordDto;
import com.example.atc.domain.pointRecord.entity.PointRecord;
import com.example.atc.domain.pointRecord.repository.PointRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointRecordService {

    private final PointRecordRepository pointRecordRepository;

    public List<PointRecord> getAllPointRecords() {
        return pointRecordRepository.findAll();
    }

    public PointRecord getPointRecordById(Long id) {
        return pointRecordRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> savePointRecord(PointRecordDto dto) {
        PointRecord pointRecord = new PointRecord();
        pointRecord.setAddSubPoint(dto.getAddSubPoint());
        pointRecord.setUsedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return ResponseEntity.status(HttpStatus.OK).body(pointRecordRepository.save(pointRecord));
    }

    public ResponseEntity<?> updatePointRecord(Long id, PointRecordDto dto) {
        Optional<PointRecord> optionalPointRecord = pointRecordRepository.findById(id);
        if(optionalPointRecord.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PointRecord not found with id " + id);
        PointRecord pointRecord = optionalPointRecord.get();
        pointRecord.setAddSubPoint(dto.getAddSubPoint());
        pointRecord.setUsedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return ResponseEntity.status(HttpStatus.OK).body(pointRecordRepository.save(pointRecord));
    }

    public void deletePointRecord(Long id) {
        pointRecordRepository.deleteById(id);
    }
}
