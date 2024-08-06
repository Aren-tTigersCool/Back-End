package com.example.atc.domain.calorieRecord.service;

import com.example.atc.domain.calorieRecord.dto.CalorieRecordDto;
import com.example.atc.domain.calorieRecord.entity.CalorieRecord;
import com.example.atc.domain.calorieRecord.repository.CalorieRecordRepository;
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
public class CalorieRecordService {

    private final CalorieRecordRepository calorieRecordRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    public List<CalorieRecord> getAllCalorieRecords() {
        return calorieRecordRepository.findAll();
    }

    public ResponseEntity<?> getCalorieRecordById(Long id) {
        Optional<CalorieRecord> calorieRecord = calorieRecordRepository.findById(id);
        if (calorieRecord.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CalorieRecord not found with id " + id);
        }
        return ResponseEntity.ok(calorieRecord.get());
    }

    public ResponseEntity<?> saveCalorieRecord(CalorieRecordDto dto) {
        if(userService.getUserById(dto.getUserId()).getStatusCode() == HttpStatus.NOT_FOUND)
            return userService.getUserById(dto.getUserId());

        CalorieRecord calorieRecord = new CalorieRecord();
        User user = (User) userService.getUserById(dto.getUserId()).getBody();

        List<CalorieRecord> todayTotalCalorie = calorieRecordRepository.findAll();
        double sum = 0;
        for (CalorieRecord calorieRecordFor : todayTotalCalorie) {
            if (isSameDay(calorieRecordFor.getUsedDate(), LocalDateTime.now())) {
                sum += calorieRecordFor.getAddSubCalorie();
            }
        }


        calorieRecord.setUser(user);
        calorieRecord.setAddSubCalorie(dto.getAddSubCalorie());
        calorieRecord.setTotalCalorie(user.getTotalCalorie() + dto.getAddSubCalorie());
        calorieRecord.setTodayTotalCalorie(sum + dto.getAddSubCalorie());
        calorieRecord.setUsedDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        user.setTotalCalorie(calorieRecord.getTotalCalorie());
        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.OK).body(calorieRecordRepository.save(calorieRecord));
    }
    private boolean isSameDay(String usedDateStr, LocalDateTime currentDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate usedDate = LocalDate.parse(usedDateStr, formatter);
        LocalDate currentDate = currentDateTime.toLocalDate();

        return usedDate.getMonth() == currentDate.getMonth() && usedDate.getDayOfMonth() == currentDate.getDayOfMonth();
    }

    public ResponseEntity<?> deleteCalorieRecord(Long id) {
        Optional<CalorieRecord> optionalCo2Record = calorieRecordRepository.findById(id);
        if (optionalCo2Record.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CalorieRecord not found with id " + id);

        calorieRecordRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("CalorieRecord deleted with id " + id);
    }
}