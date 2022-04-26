package com.revature.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.modals.Role;
import com.revature.modals.Users;
import com.revature.repositories.UserRepository;
import com.revature.service.AuthService;
import com.revature.service.UserService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {
	@Mock
	static UserRepository userRepo;
	
	@InjectMocks
	static UserService userService;
	@InjectMocks
	static AuthService authServ;
	@InjectMocks
	static Users user;
	@InjectMocks
	static List<Users> userList = new ArrayList<Users>();
	static Role employee = Role.EMPLOYEE;
	static Role cust = Role.CUSTOMER;
	
	
	@BeforeAll
	public static void startup() {
		userRepo = mock(UserRepository.class);
		Users u = new Users(1,"username","password",employee);
		Users u2 = new Users(2,"customer","custpass",cust);
		userList.add(u);
		userList.add(u2);
	}
	
	@Test
	void getAllUsersTest() {
		Mockito.when(userRepo.findAll()).thenReturn(userList);
		assertEquals(userService.getUsers(),userList);
	} 
//	@Test
//	void addUserTest() throws NoSuchAlgorithmException, UserAlreadyExistsException {
////		Role employee = Role.EMPLOYEE;
////		String hashedPW = authServ.hashingAlgo("password");
////		Users u = new Users(1,"username","password",employee);
////		u.setPassWord(hashedPW);
//		Mockito.when(userRepo.save(any(Users.class))).thenReturn(user);
//		assertEquals(user, userService.addUser(user));
//	}
	@Test
	void failAddingUserTest() throws NoSuchAlgorithmException, UserAlreadyExistsException {
		Users u = new Users(1,"username","password",Role.EMPLOYEE);
		Mockito.when(userRepo.findUsersByuserName("username")).thenReturn(u);
		assertThrows(NullPointerException.class, () -> userService.addUser(u));
		//assertNotEquals(userService.addUser(u),u);
	}
	
}
