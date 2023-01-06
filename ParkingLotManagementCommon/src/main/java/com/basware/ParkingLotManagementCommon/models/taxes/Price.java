package com.basware.ParkingLotManagementCommon.models.taxes;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Property;

@Embedded
public class Price {
    public static final String UNITS_FIELD = "units";
    public static final String CURRENCY_FIELD = "currency";

    @JsonProperty(UNITS_FIELD)
    @Property(UNITS_FIELD)
    private double units;
    @JsonProperty(CURRENCY_FIELD)
    @Property(CURRENCY_FIELD)
    private Currency currency;

    public Price(double units, Currency currency) {
        this.units = units;
        this.currency = currency;
    }

    public Price(){}

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Price{" +
                "units=" + units +
                ", currency=" + currency +
                '}';
    }
}
