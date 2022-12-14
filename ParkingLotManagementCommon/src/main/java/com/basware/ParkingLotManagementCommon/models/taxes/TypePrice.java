package com.basware.ParkingLotManagementCommon.models.taxes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TypePrice {
    public static final String TYPE_INFO_FIELD = "typeInfo";
    public static final String PRICE_FIELD = "price";

    @JsonProperty(TYPE_INFO_FIELD)
    private TypeInfo typeInfo;
    @JsonProperty(PRICE_FIELD)
    private Price price;

    public TypePrice(TypeInfo typeInfo, Price price) {
        this.typeInfo = typeInfo;
        this.price = price;
    }

    public TypePrice(){}

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
