package com.project.parkinglot.controller;

import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.dto.request.priceList.PriceListCreateRequest;
import com.project.parkinglot.service.priceList.PriceListCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/price-lists")
@RequiredArgsConstructor
@Validated
public class PriceListController {

    // TODO : Get PriceList By Id
    // TODO : GetAll PriceLists with Pagination
    // TODO : Update PriceList By Id
    // TODO : Delete PriceList By Id

    private final PriceListCreateService priceListCreateService;

    @PostMapping
    public ResponseEntity<String> createPriceList(
            @RequestBody @Valid final PriceListCreateRequest priceListCreateRequest
    ) {
        final PriceList createdPriceList = priceListCreateService
                .createPriceList(priceListCreateRequest);

        return ResponseEntity.ok(createdPriceList.getId());
    }


}
