package com.nurul.TeamManagement.Dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nurul.TeamManagement.Entity.Employee;
import com.nurul.TeamManagement.Entity.Manager;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer>{
	List<Employee> findAll();
	Employee findById(int id);
	Employee findByEmail(String email);
	Employee findByJobTitle(String jobTitle);
	List<Employee> findAllByuserName(String userName);
	Employee findByIdAndUserName(int id,String userName);
}
