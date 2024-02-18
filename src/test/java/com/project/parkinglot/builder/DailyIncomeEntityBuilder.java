package com.project.parkinglot.builder;

import com.project.parkinglot.model.entity.DailyIncomeEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DailyIncomeEntityBuilder extends BaseBuilder<DailyIncomeEntity> {

    public DailyIncomeEntityBuilder() {
        super(DailyIncomeEntity.class);
    }

    public DailyIncomeEntityBuilder withValidFields() {
        return this.withId(UUID.randomUUID().toString())
                .withDate(LocalDate.now())
                .withIncome(new BigDecimal("100.00"));
    }

    public DailyIncomeEntityBuilder withId(final String id) {
        data.setId(id);
        return this;
    }

    public DailyIncomeEntityBuilder withDate(final LocalDate date) {
        data.setDate(date);
        return this;
    }

    public DailyIncomeEntityBuilder withIncome(final BigDecimal income) {
        data.setIncome(income);
        return this;
    }

}
