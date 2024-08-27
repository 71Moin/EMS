package com.nurul.TeamManagement.Controller;

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
import com.nurul.TeamManagement.Entity.Manager;
import com.nurul.TeamManagement.Services.EncryptDecrypt;
import com.nurul.TeamManagement.Services.ManagerService;
import com.nurul.TeamManagement.Services.MaskUnmask;

@CrossOrigin
@RestController
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	ManagerService managerService;
	@Autowired
	EncryptDecrypt encryptDecrypt;
	
	@Autowired
	MaskUnmask maskUnmask;
	
	@GetMapping("/list")
	public ResponseEntity<List<Manager> >getAllManager(@RequestHeader("userName") String userName){
		List<Manager> list=null;
		
		try {
			
			if(!userName.isBlank() && !userName.isEmpty()) {
				list=managerService.getAllManagerByuserName(userName);
				for(int i=0;i<list.size();i++) {
					String email=list.get(i).getEmail();
					email=encryptDecrypt.decryptString(email);
					list.get(i).setEmail(maskUnmask.mask(email));
				}
			}else {
				return new ResponseEntity<List<Manager> >(list,HttpStatus.FORBIDDEN);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<List<Manager> >(list,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Manager> >(list,HttpStatus.OK);
	}
	@GetMapping("/detail/{id}")
	public ResponseEntity<Manager>Details(@PathVariable("id") Integer id,@RequestHeader("userName") String userName){
		
		Manager manager=null;
		try {
			if(!userName.isBlank() && !userName.isEmpty()) {
				manager=managerService.getManagerByIdAndUserName(id,userName);
				String email=manager.getEmail();
				email=encryptDecrypt.decryptString(email);
				manager.setEmail(maskUnmask.mask(email));
			}else {
				return new ResponseEntity<Manager>(manager,HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e){
			return new ResponseEntity<Manager>(manager,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Manager>(manager,HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> AddManager(@RequestBody Manager manager,@RequestHeader("userName") String userName) {
			
			try {
				if(!userName.isBlank() && !userName.isEmpty()) {
					manager.setUserName(userName);
					manager.setEmail(encryptDecrypt.encryptString(manager.getEmail()));
					managerService.save(manager);
				}else {
					return new ResponseEntity<ApiResponse>(new ApiResponse("Header Cannot be Blonk",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
				}
			}
			catch(Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Data does not saved",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<ApiResponse>(new ApiResponse("Added Manager Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	 public ResponseEntity<ApiResponse> deleteManagerById(@PathVariable("id") Integer id) {
		try {
			managerService.deleteManagerById(id);
		}
		catch(Exception e) {
			return new ResponseEntity<ApiResponse>( new ApiResponse("Somthing wrong",HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT),HttpStatus.NOT_FOUND);
		}
	    return new ResponseEntity<ApiResponse>( new ApiResponse ("Deleted Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
	 }

	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse> Update(@RequestBody Manager newManager){
		Manager manager=null;
		try {
			int id=newManager.getId();
			manager=managerService.getManagerById(id);
			if(manager!=null) {
				newManager.setEmail(manager.getEmail());
				managerService.save(newManager);
			}
		}
		catch(Exception e){
			return new ResponseEntity<ApiResponse>(new ApiResponse("Error While Updating",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse("Record Updated Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
	 }

}
