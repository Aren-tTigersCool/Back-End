package com.example.atc.domain.calorieRecord.repository;

import com.example.atc.domain.calorieRecord.entity.CalorieRecord;
import com.example.atc.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalorieRecordRepository extends JpaRepository<CalorieRecord, Long> {
    //List<PointRecord> findAllByUserAndUsedDate(User user, String usedDate);
}