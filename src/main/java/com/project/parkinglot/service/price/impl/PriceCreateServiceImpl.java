package com.project.parkinglot.service.price.impl;

import com.project.parkinglot.model.Price;
import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.dto.request.price.PriceCreateRequest;
import com.project.parkinglot.model.entity.PriceEntity;
import com.project.parkinglot.model.mapper.price.PriceDTOMapper;
import com.project.parkinglot.model.mapper.price.PriceMapper;
import com.project.parkinglot.model.mapper.priceList.PriceListMapper;
import com.project.parkinglot.repository.PriceRepository;
import com.project.parkinglot.service.parking_area.ParkingAreaCreateService;
import com.project.parkinglot.service.price.PriceCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation class named {@link PriceCreateServiceImpl} for creating price.
 */
@Service
@RequiredArgsConstructor
public class PriceCreateServiceImpl implements PriceCreateService {

    private final PriceRepository priceRepository;

    /**
     * Creates a new price.
     *
     * @param priceList          the price list to which the price belongs
     * @param priceCreateRequest the request containing the price details
     * @return the created price
     */
    @Override
    public Price createPrice(
            final PriceList priceList,
            final PriceCreateRequest priceCreateRequest
    ) {
        PriceEntity priceEntityToBeSave = PriceDTOMapper
                .mapForSaving(priceCreateRequest);

        priceEntityToBeSave.setPriceListEntity(
                PriceListMapper.toEntity(priceList)
        );

        priceRepository.save(priceEntityToBeSave);

        return PriceMapper
                .toDomainModel(priceEntityToBeSave);
    }

}
