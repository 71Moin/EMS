package com.nurul.TeamManagement.jwtAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	 return http
    	         .csrf(AbstractHttpConfigurer::disable)
    	         .authorizeHttpRequests(request -> request.requestMatchers("/ems/**").permitAll()
    	         .anyRequest().authenticated())
    	         // Send a 401 error response if user is not authentic.		 
    	         .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
    	         // no session management
    	         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
    	         // filter the request and add authentication token		 
    	         .addFilterBefore(filter,  UsernamePasswordAuthenticationFilter.class)
    	         .build();
    }

//    @Bean
//    AuthenticationManager customAuthenticationManager() {
//       return authentication -> new UsernamePasswordAuthenticationToken("randomuser123","password");
//    }

}
