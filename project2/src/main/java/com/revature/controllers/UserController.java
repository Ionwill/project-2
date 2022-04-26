package com.revature.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.modals.Users;
import com.revature.service.AuthService;
import com.revature.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	
	private UserService userService;
	private AuthService authService;
	private Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	public UserController(UserService userService, AuthService authService) {
		super();
		this.userService = userService;
		this.authService = authService;
		
	}
	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody Users newUser) throws NoSuchAlgorithmException, UserAlreadyExistsException{
		Users u = userService.addUser(newUser);
		
		return new ResponseEntity<>("User: " + u.getUserName() + 
				" was created and has a role of: " + u.getRole(),HttpStatus.ACCEPTED);
	}
	@GetMapping
	public ResponseEntity<List<Users>> getUsers(@RequestHeader(value="Authorization", required=true) String token){
		List<Users> noUsers = null;
		try {
			if(authService.verifyEmployee(token)) {
				return new ResponseEntity<> (userService.getUsers(),HttpStatus.OK);
			}
		} catch (UserNotFoundException e) {
			log.error("User list can only be accessed by proper personnel!");
			e.printStackTrace();
		}
		return new ResponseEntity<>(noUsers,HttpStatus.FORBIDDEN);
	}
	
}
