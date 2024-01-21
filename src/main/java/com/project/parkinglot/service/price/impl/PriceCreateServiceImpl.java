package com.project.parkinglot.service.price.impl;

import com.project.parkinglot.model.Price;
import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.dto.request.price.PriceCreateRequest;
import com.project.parkinglot.model.entity.PriceEntity;
import com.project.parkinglot.model.mapper.price.PriceDTOMapper;
import com.project.parkinglot.model.mapper.price.PriceMapper;
import com.project.parkinglot.model.mapper.priceList.PriceListMapper;
import com.project.parkinglot.repository.PriceRepository;
import com.project.parkinglot.service.price.PriceCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceCreateServiceImpl implements PriceCreateService {

    private final PriceRepository priceRepository;

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
