package com.project.parkinglot.service.price;

import com.project.parkinglot.model.Price;
import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.dto.request.price.PriceCreateRequest;

public interface PriceCreateService {
    Price createPrice(
            final PriceList priceList,
            final PriceCreateRequest priceCreateRequest
    );
}
