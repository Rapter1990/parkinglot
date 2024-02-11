package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.PriceListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListRepository extends JpaRepository<PriceListEntity, String> {
}
