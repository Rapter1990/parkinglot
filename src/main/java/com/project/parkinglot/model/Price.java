package com.project.parkinglot.model;

import com.project.parkinglot.common.model.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * Represents a price domain model object named {@link Price}.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Price extends BaseDomainModel {
    private String id;
    private Integer lowerBound;
    private Integer upperBound;
    private BigDecimal cost;
}
