package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.ParkingAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface ParkingAreaRepository extends JpaRepository<ParkingAreaEntity, String> {

    boolean existsByName(
            final String name
    );

    boolean existsParkingAreaEntitiesByNameAndLocation(
            final String name,
            final String location
    );

    Optional<ParkingAreaEntity> findByName(
            final String name
    );

    @Query(
            value = "SELECT SUM(park.TOTAL_COST) FROM parking_area " +
                    "LEFT JOIN park " +
                    "ON parking_area.ID = park.PARKING_AREA_ID " +
                    "WHERE DATE(park.CHECK_OUT) = :date AND parking_area.ID = :parkingAreaId", nativeQuery = true
    )
    Optional<BigDecimal> calculateDailyIncome(
            final @Param("date") LocalDate date,
            final @Param("parkingAreaId") String parkingAreaId
    );

}
