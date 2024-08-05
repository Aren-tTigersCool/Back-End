package com.example.atc.domain.pointRecord.service;

import com.example.atc.domain.pointRecord.dto.PointRecordDto;
import com.example.atc.domain.pointRecord.entity.PointRecord;
import com.example.atc.domain.pointRecord.repository.PointRecordRepository;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointRecordService {

    private final PointRecordRepository pointRecordRepository;
    private final UserService userService;

    public List<PointRecord> getAllPointRecords() {
        return pointRecordRepository.findAll();
    }

    public ResponseEntity<?> getPointRecordById(Long id) {
        Optional<PointRecord> pointRecord = pointRecordRepository.findById(id);
        if (pointRecord.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PointRecord not found with id " + id);
        }
        return ResponseEntity.ok(pointRecord.get());
    }

    public ResponseEntity<?> savePointRecord(PointRecordDto dto) {
        if(userService.getUserById(dto.getUserId()).getStatusCode() == HttpStatus.NOT_FOUND)
            return userService.getUserById(dto.getUserId());

        PointRecord pointRecord = new PointRecord();
        User user = (User) userService.getUserById(dto.getUserId()).getBody();

        int todayTotalPoints = calculateTodayTotalPoints(user);

        pointRecord.setUser(user);
        pointRecord.setAddSubPoint(dto.getAddSubPoint());
        pointRecord.setTotalPoint(user.getTotalPoint() + dto.getTotalPoint());
        pointRecord.setTodayTotalPoint(todayTotalPoints + dto.getAddSubPoint());
        pointRecord.setUsedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        user.setTotalPoint(pointRecord.getTotalPoint());
        user.setTodayTotalPoint(pointRecord.getTodayTotalPoint());
        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(pointRecordRepository.save(pointRecord));
    }

    public ResponseEntity<?> updatePointRecord(Long id, PointRecordDto dto) {
        Optional<PointRecord> optionalPointRecord = pointRecordRepository.findById(id);
        if(optionalPointRecord.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PointRecord not found with id " + id);

        PointRecord pointRecord = optionalPointRecord.get();
        User user = pointRecord.getUser();

        int todayTotalPoints = calculateTodayTotalPoints(user);

        pointRecord.setAddSubPoint(dto.getAddSubPoint());
        pointRecord.setTotalPoint(user.getTotalPoint() + dto.getAddSubPoint());
        pointRecord.setTodayTotalPoint(todayTotalPoints + dto.getAddSubPoint());
        pointRecord.setUsedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        user.setTotalPoint(pointRecord.getTotalPoint());
        user.setTodayTotalPoint(pointRecord.getTodayTotalPoint());
        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(pointRecordRepository.save(pointRecord));
    }

    public ResponseEntity<?> deletePointRecord(Long id) {
        Optional<PointRecord> optionalPointRecord = pointRecordRepository.findById(id);
        if (optionalPointRecord.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PointRecord not found with id " + id);

        pointRecordRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("PointRecord deleted with id " + id);
    }

    private int calculateTodayTotalPoints(User user) {
        List<PointRecord> records = pointRecordRepository.findAllByUserAndUsedDate(user, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return records.stream().mapToInt(PointRecord::getAddSubPoint).sum();
    }
}
