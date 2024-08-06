package com.example.atc.domain.co2Record.controller;

import com.example.atc.domain.co2Record.dto.Co2RecordDto;
import com.example.atc.domain.co2Record.entity.Co2Record;
import com.example.atc.domain.co2Record.service.Co2RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/co2-records")
public class Co2RecordController {

    private final Co2RecordService co2RecordService;

    @PostMapping
    public ResponseEntity<?> createCo2Record(@RequestBody Co2RecordDto dto) {
        return co2RecordService.saveCo2Record(dto);
    }

    @GetMapping
    public List<Co2Record> getAllCo2Records() {
        return co2RecordService.getAllCo2Records();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCo2RecordById(@PathVariable Long id) {
        return co2RecordService.getCo2RecordById(id);
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePointRecord(@PathVariable Long id, @RequestBody PointRecordDto dto) {
        return pointRecordService.updatePointRecord(id, dto);
    }
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCo2Record(@PathVariable Long id) {
        return co2RecordService.deleteCo2Record(id);
    }
}

