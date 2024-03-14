package com.project.parkinglot.builder;

import com.project.parkinglot.model.DailyIncome;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DailyIncomeBuilder extends BaseBuilder<DailyIncome> {

    public DailyIncomeBuilder() {
        super(DailyIncome.class);
    }

    public DailyIncomeBuilder withValidFields() {
        return this
                .withId(UUID.randomUUID().toString())
                .withDate(LocalDate.now())
                .withIncome(new BigDecimal("100.00"));
    }

    public DailyIncomeBuilder withId(final String id) {
        data.setId(id);
        return this;
    }

    public DailyIncomeBuilder withDate(final LocalDate date) {
        data.setDate(date);
        return this;
    }

    public DailyIncomeBuilder withIncome(final BigDecimal income) {
        data.setIncome(income);
        return this;
    }

}
