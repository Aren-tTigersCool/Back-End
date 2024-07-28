package com.example.atc.domain.pointRecord.repository;

import com.example.atc.domain.pointRecord.entity.PointRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRecordRepository extends JpaRepository<PointRecord, Long> {
}
