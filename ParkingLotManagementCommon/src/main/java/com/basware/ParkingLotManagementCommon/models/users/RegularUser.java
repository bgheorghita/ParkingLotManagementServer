package com.basware.ParkingLotManagementCommon.models.users;

public class RegularUser extends User{

	public RegularUser(){}
	public RegularUser(String name) {
		super(name, UserType.REGULAR);
	}
}
