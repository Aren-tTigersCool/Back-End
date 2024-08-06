package com.example.atc.domain.co2Record.service;

import com.example.atc.domain.co2Record.dto.Co2RecordDto;
import com.example.atc.domain.co2Record.entity.Co2Record;
import com.example.atc.domain.co2Record.repository.Co2RecordRepository;
import com.example.atc.domain.user.entity.User;
import com.example.atc.domain.user.repository.UserRepository;
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
public class Co2RecordService {

    private final Co2RecordRepository co2RecordRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    public List<Co2Record> getAllCo2Records() {
        return co2RecordRepository.findAll();
    }

    public ResponseEntity<?> getCo2RecordById(Long id) {
        Optional<Co2Record> co2Record = co2RecordRepository.findById(id);
        if (co2Record.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Co2Record not found with id " + id);
        }
        return ResponseEntity.ok(co2Record.get());
    }

    public ResponseEntity<?> saveCo2Record(Co2RecordDto dto) {
        if(userService.getUserById(dto.getUserId()).getStatusCode() == HttpStatus.NOT_FOUND)
            return userService.getUserById(dto.getUserId());

        Co2Record co2Record = new Co2Record();
        User user = (User) userService.getUserById(dto.getUserId()).getBody();

        List<Co2Record> todayTotalCo2 = co2RecordRepository.findAll();
        double sum = 0;
        for (Co2Record co2RecordFor : todayTotalCo2) {
            if (isSameDay(co2RecordFor.getUsedDate(), LocalDateTime.now())) {
                sum += co2RecordFor.getAddSubCo2();
            }
        }


        co2Record.setUser(user);
        co2Record.setAddSubCo2(dto.getAddSubCo2());
        co2Record.setTotalCo2(user.getTotalCo2() + dto.getAddSubCo2());
        co2Record.setTodayTotalCo2(sum + dto.getAddSubCo2());
        co2Record.setUsedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        user.setTotalCo2(co2Record.getTotalCo2());
        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(co2RecordRepository.save(co2Record));
    }
    private boolean isSameDay(String usedDateStr, LocalDateTime currentDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate usedDate = LocalDate.parse(usedDateStr, formatter);
        LocalDate currentDate = currentDateTime.toLocalDate();

        return usedDate.getMonth() == currentDate.getMonth() && usedDate.getDayOfMonth() == currentDate.getDayOfMonth();
    }

    /*
    public ResponseEntity<?> updatePointRecord(Long id, PointRecordDto dto) {
        Optional<PointRecord> optionalPointRecord = pointRecordRepository.findById(id);
        if(optionalPointRecord.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PointRecord not found with id " + id);

        PointRecord pointRecord = optionalPointRecord.get();
        Optional<User> userOptional = userRepository.findById(id);
        User user;
        if(userOptional.isPresent()){
            user = userOptional.get();
        } else {
            return ResponseEntity.notFound().build();
        }

        //List<PointRecord> todayRecords = pointRecordRepository.findAllByUserAndUsedDate(user, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        List<PointRecord> todayTotalPoints = pointRecordRepository.findAll();
        int sum = 0;
        for (PointRecord pointRecordFor : todayTotalPoints){
            if (pointRecordFor.getUsedDate().equals(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))){
                sum += pointRecordFor.getAddSubPoint();
            }
        }

        pointRecord.setAddSubPoint(dto.getAddSubPoint());
        pointRecord.setTotalPoint(user.getTotalPoint() + dto.getAddSubPoint());
        pointRecord.setTodayTotalPoint(sum + dto.getAddSubPoint());
        pointRecord.setUsedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        user.setTotalPoint(pointRecord.getTotalPoint());

        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(pointRecordRepository.save(pointRecord));
    }
     */

    public ResponseEntity<?> deleteCo2Record(Long id) {
        Optional<Co2Record> optionalCo2Record = co2RecordRepository.findById(id);
        if (optionalCo2Record.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Co2Record not found with id " + id);

        co2RecordRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Co2Record deleted with id " + id);
    }
}