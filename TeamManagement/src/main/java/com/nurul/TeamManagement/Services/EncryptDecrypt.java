package com.nurul.TeamManagement.Services;

import org.springframework.stereotype.Service;

import com.nurul.TeamManagement.Utils.EncryptionUtils;

@Service
public class EncryptDecrypt {
	
	public String encryptString(String str) {
        try {
            return EncryptionUtils.encrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	 public String decryptString(String str) {
	        try {
	            return EncryptionUtils.decrypt(str);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
}
