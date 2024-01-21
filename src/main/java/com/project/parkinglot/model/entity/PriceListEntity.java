package com.project.parkinglot.model.entity;

import com.project.parkinglot.common.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
