package com.revature.modals;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Users {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userID;
	@Column(unique=true, nullable=false)
	private String userName;
	
	@Column(nullable=false)
	private String passWord;
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public Users() {
		super();
	}
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "Users [userID=" + userID + ", userName=" + userName + ", passWord=" + passWord + ", role=" + role + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(passWord, role, userID, userName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		return Objects.equals(passWord, other.passWord) && role == other.role && userID == other.userID
				&& Objects.equals(userName, other.userName);
	}
	public Users(int userID, String userName, String passWord, Role role) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.passWord = passWord;
		this.role = role;
	}
	
	public Users(String userName,String passWord, Role role) {
		super();
		
		this.userName = userName;
		this.passWord = passWord;
		this.role = role;
	}
	
	
}
