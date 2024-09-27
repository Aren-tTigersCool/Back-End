package com.example.atc.domain.trashBin.repository;

import com.example.atc.domain.trashBin.entity.TrashBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrashBinRepository extends JpaRepository<TrashBin, Long> {
    @Query("SELECT t FROM TrashBin t WHERE t.latitude BETWEEN :startLat AND :endLat AND t.longitude BETWEEN :startLong AND :endLong")
    List<TrashBin> findBinsInArea(@Param("startLat") double startLat, @Param("startLong") double startLong,
                                  @Param("endLat") double endLat, @Param("endLong") double endLong);
}
