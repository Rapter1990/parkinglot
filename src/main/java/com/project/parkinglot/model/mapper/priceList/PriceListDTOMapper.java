package com.project.parkinglot.model.mapper.priceList;

import com.project.parkinglot.model.dto.request.priceList.PriceListCreateRequest;
import com.project.parkinglot.model.entity.PriceListEntity;

public class PriceListDTOMapper {

    public static PriceListEntity mapForSaving(
            final PriceListCreateRequest priceListCreateRequest
    ) {
        return PriceListEntity.builder()
                .name(priceListCreateRequest.getName())
                .build();
    }

}
