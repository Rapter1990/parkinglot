package com.project.parkinglot.model.entity;

import com.project.parkinglot.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PARKING_AREA")
public class ParkingAreaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(
            name = "NAME",
            nullable = false,
            unique = true
    )
    private String name;

    @Column(
            name = "LOCATION",
            nullable = false,
            unique = true
    )
    private String location;

    @Column(
            name = "CAPACITY",
            nullable = false
    )
    private Integer capacity;

    @Column(
            name = "CITY",
            nullable = false
    )
    private String city;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "PRICE_LIST_ID",
            referencedColumnName = "ID"
    )
    private PriceListEntity priceListEntity;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "parkingAreaEntity"
    )
    private List<ParkEntity> parkEntities;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "parkingAreaEntity"
    )
    private List<DailyIncomeEntity> dailyIncomeEntities;

}
