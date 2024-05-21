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

/**
 * Utility class named {@link FeeCalculationUtil}  for calculating fees based on vehicle type.
 */
@UtilityClass
public class FeeCalculationUtil {

    private static final Map<VehicleType, FeeCalculationStrategy> VEHICLE_TYPE_FEE_CALCULATION = Map.of(
            VehicleType.SEDAN, new SedanFeeCalculatorStrategy(),
            VehicleType.SUV, new SuvFeeCalculatorStrategy(),
            VehicleType.MINIVAN, new MinivanFeeCalculatorStrategy()
    );

    /**
     * Finds the price for the time interval.
     *
     * @param parkingAreaPriceListEntity the price list entity for the parking area
     * @param parkEntity                 the park entity
     * @return the price for the time interval
     * @throws PriceListNotFoundException if the price list is not found
     */
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

    /**
     * Checks if the spent time is within the time interval defined by the price entity.
     *
     * @param priceEntity the price entity
     * @param spentTime   the spent time
     * @return {@code true} if the spent time is within the time interval, otherwise {@code false}
     */
    private static boolean isWithinTimeInterval(
            final PriceEntity priceEntity,
            final long spentTime
    ) {
        int startHour = priceEntity.getLowerBound();
        int endHour = priceEntity.getUpperBound();
        return spentTime >= startHour && spentTime <= endHour;
    }

    /**
     * Calculates the spent time based on the check-in and check-out time of the park entity.
     *
     * @param parkEntity the park entity
     * @return the spent time in hours
     */
    private static long getSpentTime(
            final ParkEntity parkEntity
    ) {
        return Duration.between(parkEntity.getCheckIn(), parkEntity.getCheckOut()).toHours();
    }

}
