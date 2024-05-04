package com.project.parkinglot.service.priceList;

import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.dto.request.priceList.PriceListCreateRequest;

/**
 * Service interface named {@link PriceListCreateService} for creating a price list.
 */
public interface PriceListCreateService {

    /**
     * Creates a new price list.
     *
     * @param priceListCreateRequest the request containing the price list details
     * @return the created price list
     */
    PriceList createPriceList(
            final PriceListCreateRequest priceListCreateRequest
    );

}
