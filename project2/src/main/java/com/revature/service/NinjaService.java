package com.revature.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.revature.exceptions.NinjaNotFoundException;
import com.revature.modals.Ninja;
import com.revature.modals.Users;
import com.revature.repositories.NinjaRepository;
import com.revature.repositories.UserRepository;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class NinjaService {

	
	private NinjaRepository ninjaRepo;
	private UserRepository userRepo;
	private Logger log = LoggerFactory.getLogger(NinjaService.class);
	private MeterRegistry meterRegistry;
//	Counter leafVillageCounter;
//	Counter sandVillageCounter;
//	Counter mistVillageCounter;
//
//	private void initCounters() {
//		
//		//List<Ninja> ninjaVillages = ninjaRepo.findByVillage(null);
//		Ninja n = new Ninja();
//		leafVillageCounter = Counter.builder("ninjas.saved").tag("village", n.getVillage()).description("Number of ninjas").register(meterRegistry);
//		sandVillageCounter = Counter.builder("ninjas.saved").tag("village", "Hidden-Sand-Village").description("Number of ninjas").register(meterRegistry);
//		mistVillageCounter = Counter.builder("ninjas.saved").tag("village", "Hidden-Sand-Village").description("Number of ninjas").register(meterRegistry);
//
//	}
	@Autowired
	public NinjaService(NinjaRepository ninjaRepo, MeterRegistry meterRegistry){
		super();
		this.ninjaRepo = ninjaRepo;
		this.meterRegistry = meterRegistry;
	}
	
	// Gets All Ninjas in Database
	@Timed(value="ninja.time", description="Time spent retrieving ninjas by village")
	public List<Ninja> getAllNinjas(){
		
		return ninjaRepo.findAll();
	}
	// Adds/Creates new Ninja in Database
	@Transactional
	public Ninja addNinja(Ninja newNinja) {
//		if("Hidden-Leaf-Village".equals(newNinja.getVillage())) {
//			leafVillageCounter.increment();
//		} else if("Hidden-Sand-Village".equals(newNinja.getVillage())) {
//			sandVillageCounter.increment();
//		} 
		
		return ninjaRepo.save(newNinja);
	}
	// Get Ninja based on ID
	public Ninja getNinjaByID(int ID) throws NinjaNotFoundException {
		return ninjaRepo.findById(ID).orElseThrow(NinjaNotFoundException::new);
		//ninjaRepo.
	}
	@Timed(value="ninja.time", description="Time spent retrieving ninjas by village")
	public List<Ninja> getNinjasByVillage(@Param("village") String village) throws NinjaNotFoundException{
		if(village == null) {
			log.error("Village not found: " + village);
			throw new NinjaNotFoundException();
			
		}
		
		return ninjaRepo.findByVillage(village);
	
	}
	@Timed(value="ninja.time", description="Time spent retrieving ninjas by Jutsu")
	public List<Ninja> getNinjaByJutsu(@Param("jutsu") String jutsu) {
		if(jutsu == null) {
			log.error("Jutus must not be null");
		}
		return ninjaRepo.findByJutsuLike(jutsu);
		
	}
	@Timed(value="ninja.time", description="Time spent updating ninja")
	@Transactional
	public Ninja updateNinjaVillage(int ID, Ninja ninja) throws NinjaNotFoundException {
		//int ID = Integer.parseInt(id);
		Ninja n = ninjaRepo.findById(ID).orElseThrow(() -> new NinjaNotFoundException("Ninja by that: " + ID + "was not found"));
		
		ninja.setId(n.getId());
		
		return ninjaRepo.save(ninja);
		
	}
	@Transactional
	public boolean deleteNinjaByID(int ID){
		//try {
//		ninjaRepo.findById(ID).orElseThrow(() -> new NinjaNotFoundException("Ninja by that: " + ID + "was not found"));
		//} catch(NinjaNotFoundException ninja) {
			//log.error("Ninja not found with that ID: " + ID);
		//}
		ninjaRepo.deleteById(ID);
		return true;
	}
	
}
