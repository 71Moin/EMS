package com.nurul.TeamManagement.Entity;

import org.springframework.http.HttpStatus;

public class ApiResponse {
	String msg;
	int status;
	HttpStatus text;
	
	public ApiResponse() {
	}

	public ApiResponse(String msg,int status,HttpStatus text) {
		this.msg = msg;
		this.status=status;
		this.text=text;
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
	

}
