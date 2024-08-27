package com.nurul.TeamManagement.jwtAuthentication;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtHelper {

    //requirement :
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    //    public static final long JWT_TOKEN_VALIDITY =  60;
    private String secret = "employeemanagementsystembynurulislam1000043291820315fnentfhjfkjsdkjnskjnfhwertyuiokjhgxcvbnmddsggddmnbvcdfghjkjhgf";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		final Claims claims = Jwts
		         .parserBuilder()
		         .setSigningKey(getKey())
		         .build()
		         .parseClaimsJws(token).getBody(); 
		return claims;

    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {

    	return Jwts
    	         .builder()
    	         .setClaims(claims)  // set the claims
    	         .setSubject(subject)  // set the username as subject in payload
    	         .setIssuedAt(new Date(System.currentTimeMillis()))
    	         .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
    	         .signWith(getKey(), SignatureAlgorithm.HS256)  // signature part
    	         .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);		
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
     }


}
