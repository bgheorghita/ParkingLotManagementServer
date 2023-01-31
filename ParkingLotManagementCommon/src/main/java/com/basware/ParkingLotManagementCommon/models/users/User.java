package com.basware.ParkingLotManagementCommon.models.users;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

import java.util.Objects;

@Entity("users")
public class User {
	public static final String USER_TYPE_FIELD = "userType";
	public static final String VEHICLE_PLATE_NUMBER_FIELD = "vehiclePlateNumber";

	@Id
	private ObjectId objectId;

	private String name;

	@Property(VEHICLE_PLATE_NUMBER_FIELD)
	private String vehiclePlateNumber;

	@Property(USER_TYPE_FIELD)
	private UserType userType;

	public User() {}

	public User(String name, UserType userType) {
		this(name, userType, "");
	}

	public User(String name, UserType userType, String vehiclePlateNumber) {
		this.name = name;
		this.userType = userType;
		this.vehiclePlateNumber = vehiclePlateNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public ObjectId getObjectId(){
		return objectId;
	}

	public String getVehiclePlateNumber() {
		return vehiclePlateNumber;
	}

	public void setVehiclePlateNumber(String vehiclePlateNumber) {
		this.vehiclePlateNumber = vehiclePlateNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return name.equals(user.name) && userType == user.userType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, userType);
	}

	@Override
	public String toString() {
		return "User{" +
				"objectId=" + objectId +
				", name='" + name + '\'' +
				", vehiclePlateNumber='" + vehiclePlateNumber + '\'' +
				", userType=" + userType +
				'}';
	}
}
