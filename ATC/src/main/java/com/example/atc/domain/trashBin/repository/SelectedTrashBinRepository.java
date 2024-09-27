package com.example.atc.domain.trashBin.repository;

import com.example.atc.domain.trashBin.entity.SelectedTrashBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SelectedTrashBinRepository extends JpaRepository<SelectedTrashBin, Long> {
}
