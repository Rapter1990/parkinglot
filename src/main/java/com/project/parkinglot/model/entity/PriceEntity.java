package com.project.parkinglot.model.entity;

import com.project.parkinglot.common.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRICE")
public class PriceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "LOWER_BOUND")
    private Integer lowerBound;

    @Column(name = "UPPER_BOUND")
    private Integer upperBound;

    @Column(name = "COST")
    private BigDecimal cost;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "PRICE_LIST_ID",
            referencedColumnName = "ID"
    )
    private PriceListEntity priceListEntity;

}
