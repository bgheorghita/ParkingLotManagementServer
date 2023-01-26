package com.basware.ParkingLotManagementCommon.models.users;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;

import java.util.Objects;

@Entity("users")
public class User {
	public static final String USER_TYPE_FIELD = "userType";

	@Id
	private ObjectId objectId;

	private String name;

	//private Vehicle vehicle;

	@Property(USER_TYPE_FIELD)
	private UserType userType;

	public User() {}

	public User(String name, UserType userType) {
		this.name = name;
		this.userType = userType;
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
				", userType=" + userType +
				'}';
	}
}
