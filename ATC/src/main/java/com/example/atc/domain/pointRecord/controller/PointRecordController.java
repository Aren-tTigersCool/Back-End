package com.example.atc.domain.pointRecord.controller;

import com.example.atc.domain.pointRecord.dto.PointRecordDto;
import com.example.atc.domain.pointRecord.entity.PointRecord;
import com.example.atc.domain.pointRecord.service.PointRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point-records")
public class PointRecordController {

    private final PointRecordService pointRecordService;

    @PostMapping
    public ResponseEntity<?> createPointRecord(@RequestBody PointRecordDto dto) {
        return pointRecordService.savePointRecord(dto);
    }

    @GetMapping
    public List<PointRecord> getAllPointRecords() {
        return pointRecordService.getAllPointRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPointRecordById(@PathVariable Long id) {
        return pointRecordService.getPointRecordById(id);
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePointRecord(@PathVariable Long id, @RequestBody PointRecordDto dto) {
        return pointRecordService.updatePointRecord(id, dto);
    }
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePointRecord(@PathVariable Long id) {
        return pointRecordService.deletePointRecord(id);
    }
}

