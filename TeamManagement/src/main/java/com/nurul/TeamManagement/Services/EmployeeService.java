package com.nurul.TeamManagement.Services;
import org.springframework.stereotype.Service;

import com.nurul.TeamManagement.Dao.EmployeeDao;
import com.nurul.TeamManagement.Entity.Employee;
import com.nurul.TeamManagement.Entity.Manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmployeeService {
    
	@Autowired
	EmployeeDao employeeDao;
	public List<Employee>getAllEmployee(){
		List<Employee>page=null;
		page=this.employeeDao.findAll();
		return page;
	}
	public List<Employee>getAllEmployeeByuserName(String userName){
		List<Employee>page=null;
		page=this.employeeDao.findAllByuserName(userName);
		return page;
	}
	public Employee getEmployeeById(Integer id){
		Employee employee=this.employeeDao.findById((int)id);
		return employee;
	}
	public Employee getEmployeeByIdAndUserName(Integer id,String userName){
		Employee employee=this.employeeDao.findByIdAndUserName(id, userName);
		return employee;
	}
	public Employee getEmployeeByEmail(String email){
		Employee employee=this.employeeDao.findByEmail(email);
		return employee;
	}
	public Employee getEmployeeByJobTitle(String title){
		Employee employee=this.employeeDao.findByJobTitle(title);
		return employee;
	}
	public void save(Employee employee) {
		this.employeeDao.save(employee);
	}
	public void deleteEmployeeById(Integer id) {
		this.employeeDao.deleteById(id);
	}
	
}
