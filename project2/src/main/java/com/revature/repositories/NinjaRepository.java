package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.modals.Ninja;

public interface NinjaRepository extends JpaRepository<Ninja,Integer>{
	
	//@Query("SELECT * FROM ninjas WHERE village = :village")
	public List<Ninja> findByVillage(@Param("village") String village);
	
	
	//public List<Ninja> findByJutsu(@Param("jutsu") String justsu);

	
	@Query("SELECT n FROM Ninja n WHERE n.jutsu LIKE %:jutsu%")
	public List<Ninja> findByJutsuLike(@Param("jutsu") String justsu);
}
