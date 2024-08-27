package com.nurul.TeamManagement.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nurul.TeamManagement.Entity.ApiResponse;
import com.nurul.TeamManagement.Entity.EmailOtp;
import com.nurul.TeamManagement.Entity.Login;
import com.nurul.TeamManagement.Model.ForgotPasswordModel;
import com.nurul.TeamManagement.Services.EncryptDecrypt;
import com.nurul.TeamManagement.Services.LoginService;
import com.nurul.TeamManagement.Services.OtpService;
import com.nurul.TeamManagement.Services.OtpServiceImpl;

	@CrossOrigin
	@RestController
	@RequestMapping("/otp")
	public class OtpController {
		
		@Autowired
		LoginService loginService;
		
		@Autowired
		EncryptDecrypt encryptDecrypt;
		
		@Autowired
		OtpServiceImpl otpServiceImpl;
		
		 private final OtpService otpService;
		
		 @Autowired
		 public OtpController(OtpService otpService) {
		     this.otpService = otpService;
		 }
		
		 @CrossOrigin
		 @PostMapping("/send")
		 public ResponseEntity<ApiResponse> sendOtp(@RequestBody EmailOtp emailOtp) {
			 Login userAlready=loginService.getUser(emailOtp.getEmail());
		     try {
		    	 if(userAlready!=null) {
					 return new ResponseEntity<ApiResponse>(new ApiResponse("Email Already Exsist",HttpStatus.ALREADY_REPORTED.value(),HttpStatus.ALREADY_REPORTED),HttpStatus.ALREADY_REPORTED);
				 }
		    	 String generatedOtp = otpService.generateOtp(emailOtp.getEmail());
		    	 otpServiceImpl.sendOtpEmail(emailOtp.getEmail(), generatedOtp);
		    	 
			} catch (Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Error While sending OTP",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
			}
		     return new ResponseEntity<ApiResponse>(new ApiResponse("OTP Sent Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
		 }
		
		 @PostMapping("/validate")
		 public ResponseEntity<ApiResponse> validateOtp(@RequestBody EmailOtp emailOtp) {
			 
		      if(otpService.validateOtp(emailOtp.getEmail(), emailOtp.getOtp())==1) {
		    	  return new ResponseEntity<ApiResponse>(new ApiResponse("Validate Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK); 
		      }
		      else if(otpService.validateOtp(emailOtp.getEmail(), emailOtp.getOtp())==2) {
		    	  return new ResponseEntity<ApiResponse>(new ApiResponse("Time Out",HttpStatus.NOT_ACCEPTABLE.value(),HttpStatus.NOT_ACCEPTABLE),HttpStatus.BAD_REQUEST);
		      }
		      return new ResponseEntity<ApiResponse>(new ApiResponse("Wrong OTP",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
		 }
		 @CrossOrigin
		 @PostMapping("/forgotOtpSend")
		 public ResponseEntity<ApiResponse> forgotOtpSend(@RequestBody ForgotPasswordModel forgotPassword) {
			 Login user=loginService.getUser(encryptDecrypt.encryptString(forgotPassword.getEmail()));
		     try {
		    	 if(user==null) {
					 return new ResponseEntity<ApiResponse>(new ApiResponse("User Not Found",HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
				 }
		    	 String generatedOtp = otpService.generateOtp(forgotPassword.getEmail());
		    	 otpServiceImpl.sendOtpEmail(forgotPassword.getEmail(), generatedOtp);
		    	 
			} catch (Exception e) {
				return new ResponseEntity<ApiResponse>(new ApiResponse("Error While sending OTP",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
			}
		     return new ResponseEntity<ApiResponse>(new ApiResponse("OTP Sent Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
		 }
		 @PostMapping("/validateForgotOtp")
		 public ResponseEntity<ApiResponse> validateForgotOtp(@RequestBody ForgotPasswordModel forgotPassword) {
			 
		      if(otpService.validateOtp(forgotPassword.getEmail(), forgotPassword.getOtp())==1) {
		    	  return new ResponseEntity<ApiResponse>(new ApiResponse("Validate Sucessfully",HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK); 
		      }
		      else if(otpService.validateOtp(forgotPassword.getEmail(), forgotPassword.getOtp())==2) {
		    	  return new ResponseEntity<ApiResponse>(new ApiResponse("Time Out",HttpStatus.NOT_ACCEPTABLE.value(),HttpStatus.NOT_ACCEPTABLE),HttpStatus.BAD_REQUEST);
		      }
		      return new ResponseEntity<ApiResponse>(new ApiResponse("Wrong OTP",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
		 }
	}

