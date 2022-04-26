package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.NinjaNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.modals.Ninja;
import com.revature.service.AuthService;
import com.revature.service.NinjaService;
import com.revature.service.UserService;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

	private NinjaService nS;
	private AuthService aS;
	private Logger log = LoggerFactory.getLogger(NinjaController.class);
	
	@Autowired
	public NinjaController(NinjaService nS,AuthService aS) {
		//super();
		this.nS = nS;
		this.aS=aS;
	}
	// CUSTOMER view available items: Filter by -> village, jutsu
	@GetMapping
	public ResponseEntity<List<Ninja>> getAllNinjas(@RequestParam(name="village", required=false) String village, @RequestParam(required=false) String jutsu,@RequestHeader(value="Authorization",required=true) String token) throws NinjaNotFoundException, UserNotFoundException{
		List<Ninja> emptyList = new ArrayList<>();
		if(aS.verifyCustomerToken(token)) {
			log.debug("Verifying: " + token + "for proper authorization");
			MDC.put("Request ID: ", UUID.randomUUID().toString());
			if(village != null && jutsu == null) {
				//log.
				return new ResponseEntity<>(nS.getNinjasByVillage(village),HttpStatus.OK);
			} else if (village == null && jutsu != null) {
			//return new Entity<>(nS.getNinjaByJutsu(jutsu),HttpStatus.OK);
				log.info(token+"requested ninjas by jutsu");
				return new ResponseEntity<>(nS.getNinjaByJutsu(jutsu),HttpStatus.OK);
			} else {
			return new ResponseEntity<>(nS.getAllNinjas(),HttpStatus.OK);
			}
		} 
		log.error("Must be customer to view available items");
		return new ResponseEntity<>(emptyList,HttpStatus.FORBIDDEN);
	}
	
	//Adds Ninja to DB ONLY IF ROLE == EMPLOYEE
	@PostMapping
	public ResponseEntity<String> addNinja(@RequestBody Ninja newNinja, @RequestHeader(value="Authorization",required=true) String token) {
		//Users user = new Users();
		try {
			
			if(aS.verifyEmployee(token)==true) {
				MDC.put("Request ID: ", UUID.randomUUID().toString());
				MDC.put("User:Role", token);
				log.debug("Reuest to add ninja was made by: " + token);
				nS.addNinja(newNinja);
				return new ResponseEntity<>("Ninja added to database!",HttpStatus.OK);
			}
		} catch (UserNotFoundException e) {			
			log.warn("Must have proper authorization" + token);
			log.error("Unable to add Ninja to Database: Must have proper authorization" );
			//log.error(null, "Error", e.printStackTrace());
		}
		
	
		return new ResponseEntity<>("Ninja can't be added: Need proper permissions",HttpStatus.OK);
	}
	// CUSTOMER get Ninjas based off ID
	@GetMapping("/{id}")
	public ResponseEntity<Ninja> getNinjaById(@PathVariable("id") int ninjaid) throws Exception{
		
		
		return new ResponseEntity<>(nS.getNinjaByID(ninjaid), HttpStatus.OK);
		
	}
	//EMPLOYEE: ONLY person to edit information
	@PutMapping("/{id}")
	public ResponseEntity<Ninja> updateNinja(@PathVariable("id") int id,@RequestBody Ninja ninja, @RequestHeader(value="Authorization", required=true) String token) throws Exception {
		if(aS.verifyEmployee(token)) {
			log.debug("Verify request made by: " + token);
			MDC.put("Request ID: ", UUID.randomUUID().toString());
			
			return new ResponseEntity<>(nS.updateNinjaVillage(id, ninja),HttpStatus.OK);
			
		}
		MDC.put("USER:ROLE", token);
		return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
	}
	
	//DELETE Ninja to DB ONLY IF ROLE == EMPLOYEE
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteNinja(@PathVariable("id") int id, @RequestHeader(value = "Authorization", required=true) String token){
		try {
			if(aS.verifyEmployee(token)) {
				MDC.put("Request ID: ", UUID.randomUUID().toString());
				nS.deleteNinjaByID(id);
				return new ResponseEntity<>("Ninja was deleted successfully",HttpStatus.ACCEPTED);
			}
		} catch (UserNotFoundException e) {
			
			e.printStackTrace();
		}
		MDC.put("USER:ROLE", token);

		return new ResponseEntity<>("Unauthorized to perform this action!",HttpStatus.ACCEPTED);
	}
	
	
}
