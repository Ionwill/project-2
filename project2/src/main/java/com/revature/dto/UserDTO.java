package com.revature.dto;

import java.util.Objects;

import com.revature.modals.Role;
import com.revature.modals.Users;

public class UserDTO {
	private int userID;
	private String username;
	private Role role;
	
	
	public UserDTO() {
		super();
	}
	public UserDTO(Users user) {
		super();
		userID = user.getUserID();
		username = user.getUserName();
		role = user.getRole();

	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "UserDTO [userID=" + userID + ", username=" + username + ", role=" + role + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(role, userID, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return role == other.role && userID == other.userID && Objects.equals(username, other.username);
	}
	
}
