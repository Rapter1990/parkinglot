package com.project.parkinglot.model.entity;

import com.project.parkinglot.common.model.entity.BaseEntity;
import com.project.parkinglot.model.enums.ParkStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents an entity named {@link ParkEntity} for park.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PARK")
public class ParkEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "CHECK_IN")
    private LocalDateTime checkIn;

    @Column(name = "CHECK_OUT")
    private LocalDateTime checkOut;

    @Column(
            name = "TOTAL_COST",
            nullable = false,
            scale = 24,
            precision = 4
    )
    private BigDecimal totalCost;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "PARKING_AREA_ID",
            referencedColumnName = "ID"
    )
    private ParkingAreaEntity parkingAreaEntity;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "VEHICLE_ID",
            referencedColumnName = "ID"
    )
    private VehicleEntity vehicleEntity;

    @Column(name = "PARK_STATUS")
    @Enumerated(EnumType.STRING)
    private ParkStatus parkStatus;

}
