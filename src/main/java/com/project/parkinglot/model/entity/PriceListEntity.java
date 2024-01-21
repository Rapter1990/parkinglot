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
@Table(name = "PRICE_LIST")
public class PriceListEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "priceListEntity"
    )
    private List<PriceEntity> priceEntities;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "priceListEntity"
    )
    private List<ParkingAreaEntity> parkingAreaEntities;

}
