package com.project.parkinglot.service.price;

import com.project.parkinglot.model.Price;
import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.dto.request.price.PriceCreateRequest;

/**
 * Service interface named {@link PriceCreateService} for creating a price.
 */
public interface PriceCreateService {

    /**
     * Creates a new price.
     *
     * @param priceList the price list to which the price belongs
     * @param priceCreateRequest the request containing the price details
     * @return the created price
     */
    Price createPrice(
            final PriceList priceList,
            final PriceCreateRequest priceCreateRequest
    );

}
