package com.revature.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.modals.Role;
import com.revature.modals.Users;
import com.revature.repositories.UserRepository;

@Service
public class AuthService {

	private UserRepository userRepo;
	private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	public AuthService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	private String convertToHex(final byte [] messageDigest) {
		BigInteger bigint = new BigInteger(1, messageDigest);
	    String hexText = bigint.toString(16);
	      while (hexText.length() < 32) {
	         hexText = "0".concat(hexText);
	      }
	      return hexText;
	}
	
	public String hashingAlgo(String input) throws NoSuchAlgorithmException {
		String hashedInput = null;
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte [] digestedMessage = md.digest(input.getBytes(StandardCharsets.UTF_8));
		
		hashedInput = convertToHex(digestedMessage);
		
		return hashedInput;
	}

	public String login(String username, String password,Role role) throws NoSuchAlgorithmException, UserNotFoundException {
		String hashedPassword;
		Users newUser = userRepo.findUsersByuserName(username);
		hashedPassword = hashingAlgo(password);
		LOG.info("Password was hashed using secure algorithm");
		if(newUser == null || !newUser.getPassWord().equals(hashedPassword) && !newUser.getRole().equals(role)) {
			LOG.error("Username did not match: %s",username);
			throw new UserNotFoundException();
			// LOG: Login failed
			
		}
		// LOG: login successful
		String token = newUser.getUserID() + ":" + newUser.getRole().toString();
		LOG.info("Login for user: " + newUser.getUserName() + " was successful");
		return token;
	}
	public boolean register(String username, String password, Role role) throws NoSuchAlgorithmException, UserNotFoundException {
		String hashPassword;
		hashPassword= hashingAlgo(password);
		Users newUser = new Users(username,hashPassword,role);
		if(username == null) {
			LOG.warn("User with username: must not be null");
			throw new UserNotFoundException();
		}
		userRepo.save(newUser);
		LOG.info("User with username: %s was created!",username);
		return true;
	}
	
	public boolean verifyCustomerToken(String token) throws UserNotFoundException {
		if(token == null) {
			throw new UserNotFoundException();
		}
		
		String [] tokenizedToken = token.split(":");
		Users principal = userRepo.findById(Integer.valueOf(tokenizedToken[0])).orElse(null);		
		
		if(principal == null || !principal.getRole().toString().equals(tokenizedToken[1]) || !principal.getRole().toString().equals("CUSTOMER")) {
			LOG.error("User not a Customer");
			throw new UserNotFoundException();
		}
		LOG.info("Token verified successfully: " + principal.getRole());
		return true;
	}
	public boolean verifyEmployee(String token) throws UserNotFoundException {
		if(token == null) {
			throw new UserNotFoundException();
		}
		
		String [] tokenizedToken = token.split(":");
		Users principal = userRepo.findById(Integer.valueOf(tokenizedToken[0])).orElseThrow(UserNotFoundException::new);
		
		if(principal == null || !principal.getRole().toString().equals(tokenizedToken[1]) || !principal.getRole().toString().equals("EMPLOYEE")) {
			LOG.error("Unauthorized member: " + principal.getUserID() + "is not an employee");
			throw new UserNotFoundException();
			
		}
		LOG.info("Token verified successfully");
		return true;
	}
}
