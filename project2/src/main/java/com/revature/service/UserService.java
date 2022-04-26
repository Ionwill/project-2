package com.revature.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.NinjaNotFoundException;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.modals.CustomerTransaction;
import com.revature.modals.Ninja;
import com.revature.modals.Users;
import com.revature.repositories.NinjaRepository;
import com.revature.repositories.TransactionRepository;
import com.revature.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepo;
	//private NinjaRepository ninjaRepo;
	private AuthService authService;
	
	private static Logger log = LoggerFactory.getLogger(UserService.class);	//@Enumerated()
	//Role employee = Role.EMPLOYEE;
	
	@Autowired
	public UserService(UserRepository userRepo,NinjaRepository ninjaRepo,
			AuthService authService){
		super();
		this.userRepo = userRepo;
		this.authService = authService;
	}
	//Create customer account
	@Transactional
	public Users addUser(Users customer) throws NoSuchAlgorithmException,UserAlreadyExistsException {
		String passwordEntered = null;
		String hashedPassword;

		if(customer.equals(userRepo.findUsersByuserName(customer.getUserName()))) {
			log.warn(customer.getUserName() + "already exists!");
		}
		if(customer.equals(null)) {
			log.error("User can not be added: Must enter proper input");
		}
		passwordEntered = customer.getPassWord();
		hashedPassword = authService.hashingAlgo(passwordEntered);
		customer.setPassWord(hashedPassword);
		log.info("User: " + customer.getUserName() + "created and stored!" );
		return userRepo.save(customer);
	}
	//Get all users in User table
	public List<Users> getUsers(){
		
		return userRepo.findAll();	
	}
	
	
	
	//TODO:: Customer can view items purchased
	
	/*public List<CustomerTransaction> viewNinjasPurchased(int cust_id) {
		return transactionRepo.findByuser(cust_id);
		//return null;
	}
	//TODO:: Customer must be able to purchase item
	@Transactional
	public boolean purchaseNinja(Ninja ninja,Users customer) throws NinjaNotFoundException {
		//check if user role == customer 
		if(customer.equals(userRepo.findUsersByRole(customer.getRole()))) {
			//check by ID: if ninja is in ninja table 
			if(ninja.equals(ninjaRepo.findById(ninja.getId()))) {
				//store ninja in object 
				Ninja purchasedNinja = ninjaRepo.findById(ninja.getId()).orElseThrow(NinjaNotFoundException::new);
				//newNinja object is passed into transactionRepo.save()
				transactionRepo.save(purchasedNinja);
				//log.tra
				log.info("Ninja saved to transaction table");
				//remove ninja from ninja table
				ninjaRepo.delete(ninja);
				log.info("Ninja deleted from ninja table");

				log.info("Ninja was properly purchased");
				return true;
				
			}
		}
		//update rows from ninja table
		return false;
	}*/
	

	
}
