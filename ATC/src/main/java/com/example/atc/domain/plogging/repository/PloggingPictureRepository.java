package com.example.atc.domain.plogging.repository;

import com.example.atc.domain.plogging.entity.PloggingPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PloggingPictureRepository extends JpaRepository<PloggingPicture, Long> {
}
