package com.basware.ParkingLotManagementCommon.models.users;

import dev.morphia.annotations.*;
import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity("users")
@Indexes({
		@Index(options = @IndexOptions(name = "unique_username", unique = true),
				fields = @Field(value = User.USERNAME_FIELD)),
})
public class User implements UserDetails {
	public static final String USER_TYPE_FIELD = "userType";
	public static final String VEHICLE_PLATE_NUMBERS_FIELD = "vehiclePlateNumbers";
	public static final String USER_ROLES_FIELD = "roles";
	public static final String USERNAME_FIELD = "username";

	@Id
	private ObjectId objectId;

	@Property(USERNAME_FIELD)
	private String username;

	@Property(USER_ROLES_FIELD)
	private Set<Role> roles;

	@Property(VEHICLE_PLATE_NUMBERS_FIELD)
	private Set<String> vehiclePlateNumbers = new HashSet<>();

	@Property(USER_TYPE_FIELD)
	private UserType userType;

	private String password;

	public User(String username, Set<Role> roles, Set<String> vehiclePlateNumbers, UserType userType, String password) {
		this.username = username;
		this.roles = roles;
		this.vehiclePlateNumbers = vehiclePlateNumbers;
		this.userType = userType;
		this.password = password;
	}

	public User(String username, Set<Role> roles, UserType userType, String password) {
		this(username, roles, new HashSet<>(), userType, password);
	}

	public User() {

	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public Set<Role> getRoles() {
		return new HashSet<Role>(roles);
	}

	public Set<String> getVehiclePlateNumbers() {
		return new HashSet<>(vehiclePlateNumbers);
	}

	public void removeVehiclePlateNumber(String vehiclePlateNumber){
		this.vehiclePlateNumbers.remove(vehiclePlateNumber);
	}

	public UserType getUserType() {
		return userType;
	}

	public void setVehiclePlateNumbers(Set<String> plateNumbers){
		this.vehiclePlateNumbers = plateNumbers;
	}

	public void addVehiclePlateNumber(String plateNumber){
		this.vehiclePlateNumbers.add(plateNumber);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
