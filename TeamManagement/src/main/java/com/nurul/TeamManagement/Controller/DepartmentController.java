package com.nurul.TeamManagement.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nurul.TeamManagement.Entity.ApiResponse;
import com.nurul.TeamManagement.Entity.Department;
import com.nurul.TeamManagement.Entity.Employee;
import com.nurul.TeamManagement.Entity.Manager;
import com.nurul.TeamManagement.Services.DepartmentService;

@CrossOrigin
@RestController
@RequestMapping("/department")
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Department> >getAlldepartment(@RequestHeader("userName") String userName){
		List<Department> list=null;
		
		try {
			if(!userName.isBlank() && !userName.isEmpty()) {
				list=departmentService.getAllDepartmentByuserName(userName);
			}else {
				return new ResponseEntity<List<Department> >(list,HttpStatus.FORBIDDEN);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<List<Department> >(list,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Department> >(list,HttpStatus.OK);
	}
	@GetMapping("/detail/{id}")
	public ResponseEntity<Department>Details(@PathVariable("id") Integer id,@RequestHeader("userName") String userName){
		
		Department department=null;
		try {
			if(!userName.isBlank() && !userName.isEmpty()) {
				department=departmentService.getDepartmentByIdAndUserName(id,userName);
			}else {
				return new ResponseEntity<Department>(department,HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e){
			return new ResponseEntity<Department>(department,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Department>(department,HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> AddDepartment(@RequestBody Department department,@RequestHeader("userName") String userName) {
			
			try {
				if(!userName.isBlank() && !userName.isEmpty()) {
					department.setUserName(userName);
					departmentService.save(department);
				}else {
					return new ResponseEntity<ApiResponse>(new ApiResponse("Header Cannot be Blonk",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Data does not saved",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<ApiResponse>(new ApiResponse("Data Saved Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	 public ResponseEntity<ApiResponse> deleteDepartmentById(@PathVariable("id") Integer id) {
		try {
			Department department=departmentService.getDepartmentById(id);
			if(department!=null) {
				departmentService.deleteDepartmentById(id);
			}
			else {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Department not found",HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR.value(),HttpStatus.INTERNAL_SERVER_ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
	 }

	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse> Update(@RequestBody Department newDepartment){
		try {
			if(newDepartment!=null)
				departmentService.save(newDepartment);
		}
		catch(Exception e){
			return new ResponseEntity<ApiResponse>(new ApiResponse("Department Not updated",HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Department updated Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
	 }

}
