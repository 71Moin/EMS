package com.nurul.TeamManagement.Services;
import org.springframework.stereotype.Service;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.HashMap;
import java.util.Map;

@Service
public class OtpServiceImpl implements OtpService {
	
		private final Map<String, OtpData> otpDataMap = new HashMap<>();

		@Override
		 public String generateOtp(String useremail) {
		     // Generate a random 6-digit OTP
		     String otpValue= String.valueOf(100000 + (int) (Math.random() * 900000));
		     String generatedOtp = String.valueOf(otpValue);
		     String email = useremail;
		     otpDataMap.put(email, new OtpData(generatedOtp, System.currentTimeMillis() + 10 * 60 * 1000)); // 10 minutes validity
		     return generatedOtp;
		 }
		
		 @Override
		 public void sendOtpEmail(String email, String otp){
				
				  Properties properties = new Properties();
				  properties.put("mail.smtp.auth","true"); 
				  properties.put("mail.smtp.ssl.enable", "true");
				  properties.put("mail.smtp.host", "smtp.gmail.com");
				  properties.put("mail.smtp.port", "465");
				  Session session = Session.getInstance(properties, new Authenticator() {
				  
				  @Override protected PasswordAuthentication getPasswordAuthentication() {
					  return new PasswordAuthentication("esmindia82@gmail.com", "dvgjbtjfablvxdhl");
					  }
				  });
				 
		     try {
					
					  MimeMessage message = new MimeMessage(session); message.setFrom(new InternetAddress("esmindia82@gmail.com"));
					  message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
					  message.setSubject("OTP for Verification on EMS India");
					  message.setText("Welcome To Employee Management System India"
					  		+ "\nYour OTP For Varification is: " +
					  otp+
					  "\nValid only for 10 minutes"); 
					  Transport.send(message);
					 
		         System.out.println("Mail sent");
		     } catch (MessagingException e) {
		         e.printStackTrace();
		     }
		 }
		
		@Override
		public Integer validateOtp(String email, String userEnteredOtp) {
			OtpData otpData = otpDataMap.get(email);
		
		    if (otpData != null && otpData.getExpirationTime() > System.currentTimeMillis()) {
		    	if(userEnteredOtp.equals(otpData.getOtp())) {
		    		return 1;
		    	}
		    	else {
		    		return -1;
		    	}
		        
		    } else {
		        return 2;
		    }
		}
		
		private static class OtpData {
		    private final String otp;
		    private final long expirationTime;
		
		    public OtpData(String otp, long expirationTime) {
		        this.otp = otp;
		        this.expirationTime = expirationTime;
		    }
		
		    public String getOtp() {
		        return otp;
		    }
		
		    public long getExpirationTime() {
		        return expirationTime;
		    }
		}
}

