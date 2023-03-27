package com.iss.inventorymanagement.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.iss.inventorymanagement.service.MyUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	@Autowired
	private MyUserDetailsService userDetailsService;

	// Secret key to be used to sign the JWT
	private String SECRET_KEY = "secret12345123214214124141241342214312341324";

	// Function to extract the username from the jwt
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Function to exract if the jwt is for admin or not
	public boolean isAdmin(String token) {
		final Claims claims = extractAllClaims(token);
		return (int) claims.get("isAdmin") == 1 ? true : false;
	}

	// FUnction to get the expiration time of the jwt
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Function to extract all the calims embedded in the jwt as payload
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// DEcrypting the jwt using the Secretkey
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	// Function to check if the jwt is expired using the extractExpiration
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/*
	 * Function to genrate the jwt for userDetails passed as argument for the
	 * function Only called when the user is successfully authenticated
	 */
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("isAdmin", userDetailsService.isAdmin(userDetails.getUsername()));
		return createToken(claims, userDetails.getUsername());
	}

	/*
	 * Function to build the JWT and embeddin the necessary clams in the jwt
	 */
	public String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	/*
	 * Function to validate the JWT provided
	*/
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
