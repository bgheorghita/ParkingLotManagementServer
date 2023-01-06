package com.basware.ParkingLotManagementCommon.models.taxes;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.morphia.annotations.*;
import org.bson.types.ObjectId;


@Entity("prices")
@Indexes({
        @Index(options = @IndexOptions(name = "unique_type_value", unique = true),
                fields = @Field(value = TypePrice.TYPE_INFO_FIELD + "." + TypeInfo.TYPE_VALUE_FIELD)),
})
public class TypePrice {
    public static final String TYPE_INFO_FIELD = "typeInfo";
    public static final String PRICE_FIELD = "price";

    @Id
    private ObjectId objectId;
    @JsonProperty(TYPE_INFO_FIELD)
    @Property(TYPE_INFO_FIELD)
    private TypeInfo typeInfo;

    @JsonProperty(PRICE_FIELD)
    @Property(PRICE_FIELD)
    private Price price;

    public TypePrice(){}
    public TypePrice(TypeInfo typeInfo, Price price) {
        this.typeInfo = typeInfo;
        this.price = price;
    }

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
