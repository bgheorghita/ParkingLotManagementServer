package com.basware.ParkingLotManagementCommon.models.users;

public class VIPUser extends User{

	public VIPUser(){}
	public VIPUser(String name) {
		super(name, UserType.VIP);
	}

}
