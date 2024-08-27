package com.nurul.TeamManagement.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nurul.TeamManagement.Entity.Address;

@Repository
public interface AddressDao extends JpaRepository<Address, Integer>{
	  List<Address> findAll();
	  Address findById(int id);
	  Address findByCity(String City);
	 
}
