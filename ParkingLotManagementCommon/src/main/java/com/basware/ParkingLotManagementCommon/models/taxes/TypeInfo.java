package com.basware.ParkingLotManagementCommon.models.taxes;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import org.bson.types.ObjectId;

@Entity
public class TypeInfo {
    public static final String TYPE_NAME_FIELD = "typeName";
    public static final String TYPE_VALUE_FIELD = "typeValue";
    public static final String USER_IDENTIFIER = "user";
    public static final String VEHICLE_IDENTIFIER = "vehicle";
    public static final String PARKING_SPOT_IDENTIFIER = "parkingSpot";

    @Id
    private ObjectId objectId;
    @JsonProperty(TYPE_NAME_FIELD)
    @Property(TYPE_NAME_FIELD)
    private String typeName;
    @JsonProperty(TYPE_VALUE_FIELD)
    @Property(TYPE_VALUE_FIELD)
    private String typeValue;

    public TypeInfo(){}

    public TypeInfo(String typeName, String typeValue) {
        this.typeName = typeName;
        this.typeValue = typeValue;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
}