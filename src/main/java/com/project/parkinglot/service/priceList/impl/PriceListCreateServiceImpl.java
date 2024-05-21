package com.project.parkinglot.service.priceList.impl;

import com.project.parkinglot.model.Price;
import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.dto.request.priceList.PriceListCreateRequest;
import com.project.parkinglot.model.entity.PriceListEntity;
import com.project.parkinglot.model.mapper.priceList.PriceListDTOMapper;
import com.project.parkinglot.model.mapper.priceList.PriceListMapper;
import com.project.parkinglot.repository.PriceListRepository;
import com.project.parkinglot.service.price.PriceCreateService;
import com.project.parkinglot.service.priceList.PriceListCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service implementation class named {@link PriceListCreateServiceImpl} for creating price list.
 */
@Service
@RequiredArgsConstructor
public class PriceListCreateServiceImpl implements PriceListCreateService {

    private final PriceListRepository priceListRepository;
    private final PriceCreateService priceCreateService;

    /**
     * Creates a new price list.
     *
     * @param priceListCreateRequest the request containing the price list details
     * @return the created price list
     */
    @Override
    public PriceList createPriceList(
            final PriceListCreateRequest priceListCreateRequest
    ) {
        PriceListEntity priceListEntityToBeSave = PriceListDTOMapper
                .mapForSaving(priceListCreateRequest);

        priceListRepository.save(priceListEntityToBeSave);

        final PriceList priceListDomainModel = PriceListMapper
                .toDomainModel(priceListEntityToBeSave);

        if (Objects.isNull(priceListCreateRequest.getPrices())) {
            return priceListDomainModel;
        }

        final List<Price> createdPrices = priceListCreateRequest
                .getPrices()
                .stream()
                .map(
                        priceCreateRequest ->
                                priceCreateService
                                        .createPrice(
                                                priceListDomainModel,
                                                priceCreateRequest
                                        )
                )
                .toList();

        priceListDomainModel.setPrices(createdPrices);

        return priceListDomainModel;
    }

}
