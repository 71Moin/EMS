package com.nurul.TeamManagement.Services;


public interface OtpService {
 String generateOtp(String usermail);
 void sendOtpEmail(String email, String otp);
 Integer validateOtp(String email, String userEnteredOtp);
}

