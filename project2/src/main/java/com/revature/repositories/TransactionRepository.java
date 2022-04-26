package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.revature.modals.CustomerTransaction;
import com.revature.modals.Ninja;

public interface TransactionRepository extends JpaRepository<CustomerTransaction,Integer>{
	
	public List<CustomerTransaction> findByuser(@Param("user_id") int user_id);

	public void save(Ninja purchasedNinja);

	//public void save(Ninja purchasedNinja);
}
