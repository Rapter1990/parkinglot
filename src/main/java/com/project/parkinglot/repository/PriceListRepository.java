package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.PriceListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface named {@link PriceListRepository} for managing price lists.
 */
public interface PriceListRepository extends JpaRepository<PriceListEntity, String> {
}
