package com.nurul.TeamManagement.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nurul.TeamManagement.Dao.DepartmentDao;
import com.nurul.TeamManagement.Entity.Department;
import com.nurul.TeamManagement.Entity.Employee;
import com.nurul.TeamManagement.Entity.Manager;

@Service
public class DepartmentService {
	@Autowired
	DepartmentDao departmentDao;
	public List<Department>getAllDepartment(){
		List<Department>page=null;
		page=this.departmentDao.findAll();
		return page;
	}
	public List<Department>getAllDepartmentByuserName(String userName){
		List<Department>page=null;
		page=this.departmentDao.findAllByuserName(userName);
		return page;
	}
	public Department getDepartmentById(Integer id){
		Department department=this.departmentDao.findById((int)id);
		return department;
	}
	public Department getDepartmentByIdAndUserName(Integer id,String userName){
		Department department=this.departmentDao.findByIdAndUserName(id, userName);
		return department;
	}
	public Department getDepartmentByName(String name){
		Department department=this.departmentDao.findByDepartmentName(name);
		return department;
	}
	public void save(Department department) {
		this.departmentDao.save(department);
	}
	public void deleteDepartmentById(Integer id) {
		this.departmentDao.deleteById(id);
	}
}
