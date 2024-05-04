package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.ParkingAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface named {@link ParkingAreaRepository} for managing parking areas.
 */
public interface ParkingAreaRepository extends JpaRepository<ParkingAreaEntity, String> {

    /**
     * Checks if a parking area with the given name exists.
     *
     * @param name the name of the parking area
     * @return true if the parking area exists, false otherwise
     */
    boolean existsByName(
            final String name
    );

    /**
     * Checks if a parking area exists by name and location.
     *
     * @param name     the name of the parking area
     * @param location the location of the parking area
     * @return true if the parking area exists, false otherwise
     */
    boolean existsParkingAreaEntitiesByNameAndLocation(
            final String name,
            final String location
    );

    /**
     * Finds a parking area by name.
     *
     * @param name the name of the parking area
     * @return the parking area if found, otherwise empty
     */
    Optional<ParkingAreaEntity> findByName(
            final String name
    );

    /**
     * Calculates the daily income of a parking area.
     *
     * @param date          the date for which to calculate income
     * @param parkingAreaId the ID of the parking area
     * @return the total income of the parking area for the given date
     */
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
