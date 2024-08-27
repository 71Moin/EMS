package com.nurul.TeamManagement.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nurul.TeamManagement.Dao.AddressDao;
import com.nurul.TeamManagement.Entity.Address;

@Service
public class AddressService {
	@Autowired
	AddressDao addressDao;
	public List<Address>getAllAddress(){
		List<Address>page=null;
		page=this.addressDao.findAll();
		return page;
	}
	public Address getAddressById(Integer id){
		Address address=this.addressDao.findById((int)id);
		return address;
	}
	public Address getAddressByCity(String city){
		Address address=this.addressDao.findByCity(city);
		return address;
	}
	public void save(Address address) {
		this.addressDao.save(address);
	}
	public void deleteAddressById(Integer id) {
		this.addressDao.deleteById(id);
	}
}
