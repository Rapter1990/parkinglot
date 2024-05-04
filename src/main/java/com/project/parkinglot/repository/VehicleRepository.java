package com.project.parkinglot.repository;

import com.project.parkinglot.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface named {@link VehicleRepository} for managing vehicles.
 */
public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {

    /**
     * Checks if a vehicle exists by license plate.
     *
     * @param licensePlate the license plate of the vehicle
     * @return true if the vehicle exists, false otherwise
     */
    Boolean existsByLicensePlate(
            final String licensePlate
    );

    /**
     * Finds a vehicle by license plate.
     *
     * @param licensePlate the license plate of the vehicle
     * @return the vehicle if found, otherwise empty
     */
    Optional<VehicleEntity> findByLicensePlate(String licensePlate);

}
