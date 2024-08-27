package com.nurul.TeamManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nurul.TeamManagement")
public class TeamManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamManagementApplication.class, args);
	}

}
