package com.project.parkinglot.utils.price;

import com.project.parkinglot.entity.Park;
import com.project.parkinglot.entity.PriceList;
import com.project.parkinglot.entity.enums.VehicleType;
import com.project.parkinglot.exception.pricelist.PriceListNotFoundException;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@UtilityClass
public class FeeCalculationUtil {

    private static final Map<VehicleType, FeeCalculationStrategy> VEHICLE_TYPE_FEE_CALCULATION = Map.of(
            VehicleType.SEDAN, new SedanFeeCalculatorStrategy(),
            VehicleType.SUV, new SuvFeeCalculatorStrategy(),
            VehicleType.MINIVAN, new MinivanFeeCalculatorStrategy()
    );


    private static BigDecimal findPriceForTimeInterval(List<PriceList> parkingAreaPriceList, Park park) {

        long spentTime = getSpentTime(park);

        BigDecimal price = parkingAreaPriceList.stream()
                .filter(priceList -> isWithinTimeInterval(priceList, spentTime))
                .map(priceList -> priceList.getPrice().getPrice())
                .findFirst()
                // TODO : Add validation exception for this process
                .orElseThrow(() -> new PriceListNotFoundException("Not Found"));


        return VEHICLE_TYPE_FEE_CALCULATION.get(park.getVehicle().getType()).calculatePrice(price);
    }

    private static boolean isWithinTimeInterval(PriceList priceList, long spentTime) {
        int startHour = priceList.getPrice().getStartHour();
        int endHour = priceList.getPrice().getEndHour();
        return startHour <= spentTime && spentTime < endHour;
    }

    private static long getSpentTime(Park park){
        return Duration.between(park.getCheckInDate(), park.getCheckInDate()).toHours();
    }

}
