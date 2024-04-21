package com.project.parkinglot.utils.price;

import com.project.parkinglot.exception.pricelist.PriceListNotFoundException;
import com.project.parkinglot.model.entity.ParkEntity;
import com.project.parkinglot.model.entity.PriceEntity;
import com.project.parkinglot.model.entity.PriceListEntity;
import com.project.parkinglot.model.enums.VehicleType;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;

@UtilityClass
public class FeeCalculationUtil {

    private static final Map<VehicleType, FeeCalculationStrategy> VEHICLE_TYPE_FEE_CALCULATION = Map.of(
            VehicleType.SEDAN, new SedanFeeCalculatorStrategy(),
            VehicleType.SUV, new SuvFeeCalculatorStrategy(),
            VehicleType.MINIVAN, new MinivanFeeCalculatorStrategy()
    );


    public BigDecimal findPriceForTimeInterval(
            final PriceListEntity parkingAreaPriceListEntity,
            final ParkEntity parkEntity
    ) {

        long spentTime = getSpentTime(parkEntity);

        final BigDecimal price = parkingAreaPriceListEntity.getPriceEntities().stream()
                .filter(priceEntity -> isWithinTimeInterval(priceEntity, spentTime))
                .map(PriceEntity::getCost)
                .findFirst()
                .orElseThrow(() -> new PriceListNotFoundException("Not Found"));


        return VEHICLE_TYPE_FEE_CALCULATION.get(parkEntity.getVehicleEntity().getVehicleType()).calculatePrice(price);

    }

    private static boolean isWithinTimeInterval(
            final PriceEntity priceEntity,
            final long spentTime
    ) {
        int startHour = priceEntity.getLowerBound();
        int endHour = priceEntity.getUpperBound();
        return spentTime >= startHour && spentTime <= endHour;
    }

    private static long getSpentTime(
            final ParkEntity parkEntity
    ){
        return Duration.between(parkEntity.getCheckIn(), parkEntity.getCheckOut()).toHours();
    }

}
