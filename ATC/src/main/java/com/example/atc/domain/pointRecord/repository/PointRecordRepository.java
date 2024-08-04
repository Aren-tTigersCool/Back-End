package com.example.atc.domain.pointRecord.repository;

import com.example.atc.domain.pointRecord.entity.PointRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRecordRepository extends JpaRepository<PointRecord, Long> {
    List<PointRecord> findAllByUserAndUsedDate(User user, String usedDate);
}
