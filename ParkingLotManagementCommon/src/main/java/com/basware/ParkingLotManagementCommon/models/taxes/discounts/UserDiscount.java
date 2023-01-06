package com.basware.ParkingLotManagementCommon.models.taxes.discounts;

import com.basware.ParkingLotManagementCommon.models.users.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.morphia.annotations.*;
import org.bson.types.ObjectId;


@Entity("discounts")
@Indexes({
        @Index(options = @IndexOptions(name = "unique_user_type", unique = true),
                fields = @Field(value = UserDiscount.USER_TYPE_FIELD)),
})
public class UserDiscount {
    public static final String PERCENT_FIELD = "percent";
    public static final String USER_TYPE_FIELD = "userType";

    @Id
    private ObjectId objectId;

    @JsonProperty(PERCENT_FIELD)
    @Property(PERCENT_FIELD)
    private double percent;

    @JsonProperty(USER_TYPE_FIELD)
    @Property(USER_TYPE_FIELD)
    private UserType userType;

    public UserDiscount() {}
    public UserDiscount(UserType userType, double percent) {
        this.percent = percent;
        this.userType = userType;
    }


    public double getPercent(){
        return percent;
    }

    public UserType getUserType(){
        return userType;
    }
}
