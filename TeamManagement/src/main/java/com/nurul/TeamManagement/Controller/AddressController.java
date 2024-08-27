package com.nurul.TeamManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nurul.TeamManagement.Entity.Address;
import com.nurul.TeamManagement.Entity.Department;
import com.nurul.TeamManagement.Services.AddressService;
import com.nurul.TeamManagement.Services.DepartmentService;

@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	AddressService addressService;
	
	@GetMapping("/list")
	public ResponseEntity<List<Address> >getAllAddress(){
		List<Address> list=null;
		
		try {
			list=addressService.getAllAddress();
		}
		catch(Exception e) {
			return new ResponseEntity<List<Address> >(list,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Address> >(list,HttpStatus.OK);
	}
	@GetMapping("/detail/{id}")
	public ResponseEntity<Address>Details(@PathVariable("id") Integer id){
		
		Address address=null;
		try {
			address=addressService.getAddressById(id);
		}
		catch(Exception e){
			return new ResponseEntity<Address>(address,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Address>(address,HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<String> AddAddress(@RequestBody Address address) {
			
			try {
				addressService.save(address);
			}
			catch(Exception e) {
				return new ResponseEntity<String>("Data does not saved",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<String>("Data Saved Sucessfully",HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	 public ResponseEntity<String> deleteDepartmentById(@PathVariable("id") Integer id) {
		try {
			addressService.deleteAddressById(id);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Somthing wrong",HttpStatus.NOT_FOUND);
		}
	    return new ResponseEntity<String>("Deleted Sucessfully",HttpStatus.OK);
	 }

	
	@PutMapping("/update/{id}")
	public ResponseEntity<Address> Update(@PathVariable("id") Integer id,@RequestBody Address newAddress){
		Address address=null;
		try {
			address=addressService.getAddressById(id);
			if(newAddress.getCity()!=null)
				address.setCity(newAddress.getCity());
			if(newAddress.getState()!=null)
				address.setState(newAddress.getState());
			if(newAddress.getCountry()!=null)
				address.setCountry(newAddress.getCountry());
			if(newAddress.getPin()!=null)
				address.setPin(newAddress.getPin());
		}
		catch(Exception e){
			return new ResponseEntity<Address>(address,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Address>(address,HttpStatus.OK);
	 }

}
