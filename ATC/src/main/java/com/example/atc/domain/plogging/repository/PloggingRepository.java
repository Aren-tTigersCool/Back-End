package com.example.atc.domain.plogging.repository;

import com.example.atc.domain.plogging.entity.PloggingRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PloggingRepository extends JpaRepository<PloggingRepo,Long> {

}
