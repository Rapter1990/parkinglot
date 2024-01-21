package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.PriceListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListRepository extends JpaRepository<PriceListEntity, String> {
}
