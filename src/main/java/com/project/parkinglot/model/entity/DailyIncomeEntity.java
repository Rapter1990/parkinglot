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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DAILY_INCOME")
public class DailyIncomeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "INCOME")
    private BigDecimal income;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "PARKING_AREA_ID",
            referencedColumnName = "ID"
    )
    private ParkingAreaEntity parkingAreaEntity;


}
