package com.revature.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.modals.Role;
import com.revature.modals.Users;
import com.revature.repositories.UserRepository;
import com.revature.service.AuthService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceTest {
	@Mock
	private UserRepository userRepo;
	@InjectMocks
	private AuthService authService;
	
	@Test
	void loginTest() throws NoSuchAlgorithmException, UserNotFoundException {
		
		Users user = new Users(1,"Employee1", "Password",Role.EMPLOYEE);
		Mockito.when(userRepo.findUsersByuserName("Employee1")).thenReturn(user);
		String hashedPW = authService.hashingAlgo("Password");
		user.setPassWord(hashedPW);
		assertEquals(authService.login("Employee1", hashedPW, Role.EMPLOYEE),"1:"+Role.EMPLOYEE);
		
		
	}
	
	@Test
	void failedLogin() throws NoSuchAlgorithmException, UserNotFoundException {
	 /*	Users user = new Users(1,"Employee1", "Password",Role.EMPLOYEE);
		Mockito.when(userRepo.findUsersByuserName("Employee2")).thenReturn(user);
		assertNotEquals(authService.login("Employee2", "Password", Role.EMPLOYEE), user); */
		
		assertThrows(UserNotFoundException.class, () -> authService.login("Employee1", "password",Role.EMPLOYEE));
	}
	@Test
	void registerTest() throws NoSuchAlgorithmException, UserNotFoundException{
		String hashedPW = authService.hashingAlgo("pass");
		Users newuser = new Users("T",hashedPW,Role.CUSTOMER);
		//newuser.setPassWord(hashedPW);
		Mockito.when(userRepo.save(newuser)).thenReturn(newuser);
		//assertEquals(authService.register("Test", hashedPW, Role.CUSTOMER), newuser);
		assertTrue(authService.register("T", hashedPW, Role.CUSTOMER));
		//boolean successfulRegister = 
		///assertTrue(authService.register("Test", hashedPW, Role.CUSTOMER),"True");
	}
	
	@Test
	void failedRegisterTest() throws UserAlreadyExistsException, NoSuchAlgorithmException{
		Users user = new Users("Test", "pass", Role.CUSTOMER);
		Mockito.when(userRepo.findUsersByuserName("Test")).thenReturn(user);
		//assertNotEquals(authService.register("Employee2", "Password", Role.EMPLOYEE), user); 

		
		assertThrows(UserNotFoundException.class, () -> authService.register(null, "pass", Role.CUSTOMER));
	}
	
	@Test
	void  verifyCustomerTokenTest() {
		
	}
	
	
}
