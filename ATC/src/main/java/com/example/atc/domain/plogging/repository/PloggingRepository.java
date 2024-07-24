package com.example.atc.domain.plogging.repository;

import com.example.atc.domain.plogging.entity.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PloggingRepository extends JpaRepository<Plogging,Long> {

}
