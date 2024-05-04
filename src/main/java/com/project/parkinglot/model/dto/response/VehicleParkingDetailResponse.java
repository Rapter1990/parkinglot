package com.project.parkinglot.model.dto.response;

import com.project.parkinglot.common.model.BaseDomainModel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * A response class named {@link VehicleParkingDetailResponse} representing the vehicle parking detail response.
 */
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class VehicleParkingDetailResponse extends BaseDomainModel {

    private String licensePlate;
    private List<ParkDetailResponse> parkDetails;

}
