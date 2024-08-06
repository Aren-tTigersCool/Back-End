package com.example.atc.domain.calorieRecord.controller;

import com.example.atc.domain.calorieRecord.dto.CalorieRecordDto;
import com.example.atc.domain.calorieRecord.entity.CalorieRecord;
import com.example.atc.domain.calorieRecord.service.CalorieRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calorie-records")
public class CalorieRecordController {

    private final CalorieRecordService calorieRecordService;

    @PostMapping
    public ResponseEntity<?> createCalorieRecord(@RequestBody CalorieRecordDto dto) {
        return calorieRecordService.saveCalorieRecord(dto);
    }

    @GetMapping
    public List<CalorieRecord> getAllCalorieRecords() {
        return calorieRecordService.getAllCalorieRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCalorieRecordById(@PathVariable Long id) {
        return calorieRecordService.getCalorieRecordById(id);
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePointRecord(@PathVariable Long id, @RequestBody PointRecordDto dto) {
        return pointRecordService.updatePointRecord(id, dto);
    }
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCalorieRecord(@PathVariable Long id) {
        return calorieRecordService.deleteCalorieRecord(id);
    }
}