package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.modals.Role;
import com.revature.modals.Users;

public interface UserRepository extends JpaRepository<Users,Integer>{
	
	public Users findUsersByuserName(String username);
	public Users findUsersByRole(Role role);
}
