package com.project.parkinglot.service.priceList;

import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.dto.request.priceList.PriceListCreateRequest;

public interface PriceListCreateService {
    PriceList createPriceList(
            final PriceListCreateRequest priceListCreateRequest
    );
}
