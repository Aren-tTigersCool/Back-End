package com.example.atc.domain.co2Record.controller;

import com.example.atc.domain.co2Record.dto.Co2RecordDto;
import com.example.atc.domain.co2Record.entity.Co2Record;
import com.example.atc.domain.co2Record.service.Co2RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/calculate")
    public ResponseEntity<Map<String, Double>> calculateDistance(@RequestParam double distance) {
        // 계산식
        double kcal = 3.5 * 70 * distance / 5;
        double co2Kg = distance / 11.06 * 1.868;
        double trees = 0.02335 * distance;

        // 결과를 Map에 담아 반환
        Map<String, Double> result = new HashMap<>();
        result.put("kcal", kcal);
        result.put("co2Kg", co2Kg);
        result.put("trees", trees);

        return ResponseEntity.ok(result);
    }

}

