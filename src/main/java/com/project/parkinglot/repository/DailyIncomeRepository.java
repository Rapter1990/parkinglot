package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.DailyIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyIncomeRepository extends JpaRepository<DailyIncomeEntity, String> {

}
