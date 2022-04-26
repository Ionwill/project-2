package com.revature.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.revature.exceptions.NinjaNotFoundException;
import com.revature.modals.Ninja;
import com.revature.repositories.NinjaRepository;
import com.revature.service.NinjaService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class NinjaServiceTest {
	
	@Mock
	private NinjaRepository ninjaRepo;
	
	static List<Ninja> ninjaList;
	
	@InjectMocks
	NinjaService ninjaService;
	
	//public void setup() {
		
	
	@Test
	void getAllNinjas() {
		List<Ninja> ninjaList = new ArrayList<Ninja>();
		
		Ninja n1 = new Ninja(1,"Sasuke Uchiha", "Hidden-Leaf-Village","Amaterasu","Uchiha Clan",65);
		Ninja n2 = new Ninja(2,"Naruto Uzumaki", "Hidden-Leaf-Village","Rasengan","7th Hokage",60);
		Ninja n3 = new Ninja(3,"Kakashi Hatake", "Hidden-Leaf-Village","Lighning Blade","6th Hokage",67);
		Ninja n4 = new Ninja(4,"Gaara", "Hidden-Sand-Village","Sand Manipulation","Third Kazekage",65);
		Ninja n5 = new Ninja(1,"Sasuke Uchiha", "Hidden-Leaf-Village","Amaterasu","Uchiha Clan",65);
		
		ninjaList.add(n1);
		ninjaList.add(n2);
		ninjaList.add(n3);
		ninjaList.add(n4);
		ninjaList.add(n5);
	
		Mockito.when(ninjaRepo.findAll()).thenReturn(ninjaList);
		assertEquals(ninjaService.getAllNinjas(), ninjaList);
	}
	@Test
	void addNinja() {
		Ninja newNinja = new Ninja(1,"Sasuke Uchiha", "Hidden-Leaf-Village","Amaterasu","Uchiha Clan",65);
		
		Mockito.when(ninjaRepo.save(newNinja)).thenReturn(newNinja);
		assertEquals(ninjaService.addNinja(newNinja), newNinja);
	}
	@Test
	void getNinjaByID() throws NinjaNotFoundException {
		Ninja ninja = new Ninja(1,"Sasuke Uchiha", "Hidden-Leaf-Village","Amaterasu","Uchiha Clan",65);
		
		Mockito.when(ninjaRepo.findById(1)).thenReturn(Optional.of(ninja));
		assertEquals(ninjaService.getNinjaByID(1), ninja);
	}
	@Test
	void failNinjaByID() {
		Ninja ninja = new Ninja(1,"Sasuke Uchiha", "Hidden-Leaf-Village","Amaterasu","Uchiha Clan",65);
		boolean thrown = false;
		try {
			ninjaService.getNinjaByID(0);
		} catch(NinjaNotFoundException e) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	@Test
	void updateNinja() throws NinjaNotFoundException {
		Ninja updatedNinja = new Ninja(1,"Sasuke Uchiha", "Hidden-Leaf-Village","Amaterasu,Lighning Blade","Uchiha Clan",65);
		
		Mockito.when(ninjaRepo.findById(1)).thenReturn(Optional.of(updatedNinja));
		Mockito.when(ninjaRepo.save(updatedNinja)).thenReturn(updatedNinja);
		assertEquals(ninjaService.updateNinjaVillage(1, updatedNinja), updatedNinja);
	}
	@Test
	void failUpdateNinja() {
		Ninja ninja = new Ninja(1,"Sasuke Uchiha", "Hidden-Leaf-Village","Test Jutstu","Uchiha Clan",65);
		boolean thrown = false;
		
		try {
			//Mockito.when(ninjaRepo.findById(1)).thenReturn(Optional.of(ninja));
			//Mockito.when(ninjaRepo.save(updatedNinja)).thenReturn(updatedNinja);
			ninjaService.updateNinjaVillage(0,ninja);
		} catch (NinjaNotFoundException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	@Test
	void ninjaByJutsu() {
		List<Ninja> ninjaList = new ArrayList<Ninja>();

		Ninja n3 = new Ninja(3,"Kakashi Hatake", "Hidden-Leaf-Village","Lightning Blade","6th Hokage",67);
		ninjaList.add(n3);
		Mockito.when(ninjaRepo.findByJutsuLike("Lightning Blade")).thenReturn(ninjaList);
		assertEquals(ninjaService.getNinjaByJutsu("Lightning Blade"),ninjaList);
	}
	@Test
	void ninjaByVillage() throws NinjaNotFoundException {
		List<Ninja> ninjaList = new ArrayList<Ninja>();
		Ninja ninja = new Ninja(3,"Kakashi Hatake", "Hidden-Leaf-Village","Lighning Blade","6th Hokage",67);
		ninjaList.add(ninja);
		Mockito.when(ninjaRepo.findByVillage("Hidden-Leaf-Village")).thenReturn(ninjaList);
		assertEquals(ninjaService.getNinjasByVillage("Hidden-Leaf-Village"),ninjaList);

		
	}
	@Test
	void failNinjaByVillage() throws NinjaNotFoundException{
		List<Ninja> ninjaList = new ArrayList<Ninja>();
		Ninja ninja = new Ninja(3,"Kakashi Hatake", "Hidden-Rain-Village","Lighning Blade","6th Hokage",67);
		ninjaList.add(ninja);
		Mockito.when(ninjaRepo.findByVillage("Hidden-Leaf-Village")).thenReturn(ninjaList);
		assertThrows(NinjaNotFoundException.class,() -> ninjaService.getNinjasByVillage(null) );
	}
	@Test
	void deleteNinja() throws NinjaNotFoundException {
		Ninja ninja = new Ninja(1,"Sasuke Uchiha", "Hidden-Leaf-Village","Amaterasu,Lightning Blade","Uchiha Clan",65);
		Mockito.when(ninjaRepo.findById(1)).thenReturn(Optional.of(ninja));
		assertTrue(ninjaService.deleteNinjaByID(1));
	}
}
