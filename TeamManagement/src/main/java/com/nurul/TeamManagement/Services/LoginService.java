package com.nurul.TeamManagement.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nurul.TeamManagement.Dao.LoginDao;
import com.nurul.TeamManagement.Entity.Login;

@Service
public class LoginService {
	@Autowired LoginDao loginDao;
	
	public Login getUser(String user ){
		Login login=this.loginDao.findByUsername(user);
		return login;	
	}

	public void save(Login login) {
		loginDao.save(login);
	}
}
