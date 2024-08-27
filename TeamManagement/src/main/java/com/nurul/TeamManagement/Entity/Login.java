package com.nurul.TeamManagement.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="LOGIN")
public class Login {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Long id;
	
	@Column(name = "USERNAME",nullable = false,unique = true)
	private String username;
	
	@Column(name = "PASSWORD",nullable = false)
	private String password;
	
	@Column(name = "STATUS")
	char status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	

}
