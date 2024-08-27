package com.nurul.TeamManagement.Utils;

public class MaskingUtils {
	public static String maskData(String value) {
		int len=value.length();
		String maskvalue=value.substring(0,3);
		for(int i=1;i<=10;i++)
			maskvalue+="@";
		maskvalue+=value.substring(len-3);
		return maskvalue;
	}

}
