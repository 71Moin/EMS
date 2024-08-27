package com.nurul.TeamManagement.Services;

import org.springframework.stereotype.Service;

import com.nurul.TeamManagement.Utils.MaskingUtils;

@Service
public class MaskUnmask {
	
	public String mask(String str) {
		try {
            return MaskingUtils.maskData(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

}
