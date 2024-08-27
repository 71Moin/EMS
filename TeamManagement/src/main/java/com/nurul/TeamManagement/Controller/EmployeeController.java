package com.nurul.TeamManagement.Controller;
import java.time.LocalDate;
import java.util.*;
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
import com.nurul.TeamManagement.Entity.Employee;
import com.nurul.TeamManagement.Services.EmployeeService;
import com.nurul.TeamManagement.Services.EncryptDecrypt;
import com.nurul.TeamManagement.Services.MaskUnmask;

@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EncryptDecrypt encryptDecrypt;
	
	@Autowired
	MaskUnmask maskUnmask;
	
	
	@GetMapping("/list")
	public ResponseEntity<List<Employee> >getAllemployee(@RequestHeader("userName") String userName){
		List<Employee> list=null;
		
		try {
			if(!userName.isBlank() && !userName.isEmpty()) {
				list=employeeService.getAllEmployeeByuserName(userName);
				for(int i=0;i<list.size();i++) {
					String email=list.get(i).getEmail();
					email=encryptDecrypt.decryptString(email);
					list.get(i).setEmail(maskUnmask.mask(email));
				}
			}else {
				return new ResponseEntity<List<Employee> >(list,HttpStatus.FORBIDDEN);
			}
			 
		}
		catch(Exception e) {
			return new ResponseEntity<List<Employee> >(list,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Employee> >(list,HttpStatus.OK);
	}
	@GetMapping("/detail/{id}")
	public ResponseEntity<Employee>Details(@PathVariable("id") Integer id,@RequestHeader("userName") String userName){
		
		Employee employee=null;
		try {
			if(!userName.isBlank() && !userName.isEmpty()) {
				employee=employeeService.getEmployeeByIdAndUserName(id,userName);
				String email=employee.getEmail();
				email=encryptDecrypt.decryptString(email);
				employee.setEmail(maskUnmask.mask(email));
			}else {
				return new ResponseEntity<Employee>(employee,HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e){
			return new ResponseEntity<Employee>(employee,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Employee>(employee,HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> AddEmployee(@RequestBody Employee employee,@RequestHeader("userName") String userName) {
			
			try {
//				MultipartFile file=employee.getImageFile();
//				if (file != null && !file.isEmpty()) {
//		            byte[] imageData = file.getBytes();
//		            employee.setImage(imageData);
//		        }
				if(!userName.isBlank() && !userName.isEmpty()) {
					employee.setUserName(userName);
					employee.setEmail(encryptDecrypt.encryptString(employee.getEmail()));
					employee.setCreateTime(LocalDate.now());
					employeeService.save(employee);
				}else {
					return new ResponseEntity<ApiResponse>(new ApiResponse("Header Cannot be Blonk",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Data does not saved",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<ApiResponse>(new ApiResponse("Added Employee Record Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	 public ResponseEntity<ApiResponse> deleteEmployeeById(@PathVariable("id") Integer id) {
		try {
			employeeService.deleteEmployeeById(id);
		}
		catch(Exception e) {
			return new ResponseEntity<ApiResponse>( new ApiResponse("Somthing wrong",HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT),HttpStatus.NOT_FOUND);
		}
	    return new ResponseEntity<ApiResponse>( new ApiResponse ("Deleted Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
	 }

	@CrossOrigin
	@PutMapping("/update")
	public ResponseEntity<ApiResponse> UpdateEmployee(@RequestBody Employee newEmployee){
		Employee employee=null;
		try {
			int id=newEmployee.getId();
			employee=employeeService.getEmployeeById(id);
			if(employee!=null) {
				newEmployee.setEmail(employee.getEmail());
				newEmployee.setCreateTime(employee.getCreateTime());
				employeeService.save(newEmployee);
			}
		}
		catch(Exception e){
			return new ResponseEntity<ApiResponse>(new ApiResponse("Error While Updating",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Record Updated Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
	 }
}
