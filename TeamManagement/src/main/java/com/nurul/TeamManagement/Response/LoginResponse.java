package com.nurul.TeamManagement.Response;

import org.springframework.http.HttpStatus;

public class LoginResponse {
	private String msg;
	private int status;
	private HttpStatus text;
	private String userName;
	private String Authorization;
	public LoginResponse() {
	}

	public LoginResponse(String msg,int status,HttpStatus text,String userName,String Authorization) {
		this.msg = msg;
		this.status=status;
		this.text=text;
		this.userName=userName;
		this.Authorization=Authorization;
	}

	
	public String getAuthorization() {
		return Authorization;
	}

	public void setAuthorization(String authorization) {
		Authorization = authorization;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public HttpStatus getText() {
		return text;
	}

	public void setText(HttpStatus text) {
		this.text = text;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
