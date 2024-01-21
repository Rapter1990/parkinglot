package com.project.parkinglot.model;

import com.project.parkinglot.common.model.BaseDomainModel;
import com.project.parkinglot.model.entity.PriceListEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Domain Model of {@link PriceListEntity}
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PriceList extends BaseDomainModel {
    private String id;
    private String name;
    private List<Price> prices;
}
