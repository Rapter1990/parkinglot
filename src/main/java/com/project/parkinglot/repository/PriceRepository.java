package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<PriceEntity, String> {

}
