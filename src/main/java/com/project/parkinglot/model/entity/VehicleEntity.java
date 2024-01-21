package com.project.parkinglot.model.entity;

import com.project.parkinglot.common.model.entity.BaseEntity;
import com.project.parkinglot.model.enums.VehicleType;
import com.project.parkinglot.security.model.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VEHICLE")
public class VehicleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(
            name = "LICENCE_PLATE",
            nullable = false,
            unique = true
    )
    private String licensePlate;

    @Column(name = "VEHICLE_TYPE")
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "vehicleEntity"
    )
    private List<ParkEntity> parkEntities;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "USER_ID",
            referencedColumnName = "ID"
    )
    private User user;

}
