package com.nurul.TeamManagement.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nurul.TeamManagement.Entity.ApiResponse;
import com.nurul.TeamManagement.Entity.Login;
import com.nurul.TeamManagement.Model.ForgotPasswordModel;
import com.nurul.TeamManagement.Response.LoginResponse;
import com.nurul.TeamManagement.Services.EncryptDecrypt;
import com.nurul.TeamManagement.Services.LoginService;
import com.nurul.TeamManagement.jwtAuthentication.JwtHelper;

@CrossOrigin
@RestController
@RequestMapping("/ems")
public class LoginController {
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired 
	LoginService loginService;
	
	@Autowired
	EncryptDecrypt encryptDecrypt;
	
	@Autowired
    private JwtHelper helper;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse>UserLogin(@RequestBody Login user){
		
		Login login=null;
		try {
			String usr=user.getUsername();
			login=loginService.getUser(encryptDecrypt.encryptString(usr));
			if(login.getUsername().equals(encryptDecrypt.encryptString(usr)) &&
					login.getPassword().equals(encryptDecrypt.encryptString(user.getPassword()))) {
				if(login.getStatus()!='A') {
					return new ResponseEntity<LoginResponse>(new LoginResponse("Account Not Active",3000,
							HttpStatus.UNAUTHORIZED,"",""),HttpStatus.NOT_FOUND);
				}
				UserDetails userDetails = User.builder()
		                .username(usr)
		                .password(passwordEncoder.encode(user.getPassword()))
		                .build();

		        inMemoryUserDetailsManager.createUser(userDetails);
//		        System.out.println(userDetails.toString());
		        String token = this.helper.generateToken(userDetails);
		        return new ResponseEntity<LoginResponse>(new LoginResponse("Login Success",HttpStatus.OK.value(),
						HttpStatus.OK,user.getUsername(),token),HttpStatus.OK);
			}
			else {
				return new ResponseEntity<LoginResponse>(new LoginResponse("Invalid Password",HttpStatus.UNAUTHORIZED.value(),
						HttpStatus.UNAUTHORIZED,"",""),HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e){
			return new ResponseEntity<LoginResponse>(new LoginResponse("User Not Found",HttpStatus.NOT_FOUND.value(),
					HttpStatus.NOT_FOUND,"",""),HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> RegiserUser(@RequestBody Login user) {
			
			try {
				user.setStatus('A');
				user.setUsername(encryptDecrypt.encryptString(user.getUsername()));
				user.setPassword(encryptDecrypt.encryptString(user.getPassword()));
				loginService.save(user);
			}
			catch(Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Data does not saved",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<ApiResponse>(new ApiResponse("New Account Created",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.CREATED);
	}
	@PostMapping("/updPass")
	public ResponseEntity<ApiResponse> RegiserUser(@RequestBody ForgotPasswordModel updUser) {
			Login user=loginService.getUser(encryptDecrypt.encryptString(updUser.getEmail()));
			try {
				if(user!=null) {
					user.setPassword(encryptDecrypt.encryptString(updUser.getPassword()));
					loginService.save(user);
				}else {
					return new ResponseEntity<ApiResponse>(new ApiResponse("User Does Not Exsist",HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
				}
				
			}
			catch(Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Password Does Not Saved",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<ApiResponse>(new ApiResponse("Password Updated Successfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.CREATED);
	}

}
