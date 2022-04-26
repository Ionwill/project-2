package com.revature.controllers;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.UserDTO;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.modals.Role;
import com.revature.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam(name="username") String username, @RequestParam(name="password") String password, @RequestParam(name="role") Role role) throws NoSuchAlgorithmException, UserNotFoundException{
		
		String token = authService.login(username, password,role);
		LOG.debug("Authentication Service used to authenticate: " + username);
		if(token == null) {
			LOG.warn("Token must not be null");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		HttpHeaders header = new HttpHeaders();
		// Add token or session cookie
		
		//String token = authService.login(user);
		
		header.set("Authorization", token);
		LOG.info("Login successful by: " + token);
		return new ResponseEntity<>("Login Successful.",header,HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestParam(name="username") String username, @RequestParam(name="password") String password
											, @RequestParam(name="role") Role role) throws NoSuchAlgorithmException, UserNotFoundException{
		boolean registered = authService.register(username, password, role);
					
		//if()
		HttpHeaders header = new HttpHeaders();
		//header.set("Authorization", token);
		LOG.info(username+": " + " registered successfully");
		return new ResponseEntity<>("Register successful",HttpStatus.OK);

	}
}
