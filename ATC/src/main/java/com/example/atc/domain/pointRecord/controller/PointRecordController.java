package com.example.atc.domain.pointRecord.controller;

import com.example.atc.domain.pointRecord.entity.PointRecordEntity;
import com.example.atc.domain.pointRecord.service.PointRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/point-records")
public class PointRecordController {

    @Autowired
    private PointRecordService pointRecordService;

    @GetMapping
    public List<PointRecordEntity> getAllPointRecords() {
        return pointRecordService.getAllPointRecords();
    }

    @GetMapping("/{id}")
    public PointRecordEntity getPointRecordById(@PathVariable Long id) {
        return pointRecordService.getPointRecordById(id);
    }

    @PostMapping
    public PointRecordEntity createPointRecord(@RequestBody PointRecordEntity pointRecord) {
        return pointRecordService.savePointRecord(pointRecord);
    }

    @PutMapping("/{id}")
    public PointRecordEntity updatePointRecord(@PathVariable Long id, @RequestBody PointRecordEntity pointRecord) {
        pointRecord.setUserRecordId(id);
        return pointRecordService.savePointRecord(pointRecord);
    }

    @DeleteMapping("/{id}")
    public void deletePointRecord(@PathVariable Long id) {
        pointRecordService.deletePointRecord(id);
    }
}

