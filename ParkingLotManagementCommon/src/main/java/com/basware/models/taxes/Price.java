package com.basware.models.taxes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Price {
    private double units;
    private Currency currency;

    @Override
    public String toString() {
        return "Price{" +
                "units=" + units +
                ", currency=" + currency +
                '}';
    }
}
