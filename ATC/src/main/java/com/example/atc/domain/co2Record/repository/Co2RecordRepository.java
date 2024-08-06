package com.example.atc.domain.co2Record.repository;

import com.example.atc.domain.co2Record.entity.Co2Record;
import com.example.atc.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Co2RecordRepository extends JpaRepository<Co2Record, Long> {
    //List<PointRecord> findAllByUserAndUsedDate(User user, String usedDate);
}