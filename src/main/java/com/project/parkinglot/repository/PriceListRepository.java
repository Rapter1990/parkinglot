package com.project.parkinglot.repository;

import com.project.parkinglot.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PriceListRepository extends JpaRepository<PriceList, UUID> {
}
