package com.nurul.TeamManagement.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nurul.TeamManagement.Entity.Login;

@Repository
public interface LoginDao extends JpaRepository<Login, Long>{
	Login findByUsername(String user);
}
