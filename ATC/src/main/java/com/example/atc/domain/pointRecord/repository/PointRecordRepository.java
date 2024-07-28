package com.example.atc.domain.pointRecord.repository;

import com.example.atc.domain.pointRecord.entity.PointRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRecordRepository extends JpaRepository<PointRecordEntity, Long> {
}
