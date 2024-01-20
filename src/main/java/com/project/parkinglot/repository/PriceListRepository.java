package com.project.parkinglot.repository;

import com.project.parkinglot.entity.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceListRepository extends JpaRepository<PriceList, String> {
}
