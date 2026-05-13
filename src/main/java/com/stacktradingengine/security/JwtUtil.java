package com.stacktradingengine.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final String SECRET = "mysecretkeymysecretkeymysecretkey123456";

	private final long EXPIRATION = 1000 * 60 * 60 * 24;

	private Key getSigningKey() {

		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}

	public String generateToken(String email, String role) {

		return Jwts.builder().setSubject(email).claim("role", role).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractEmail(String token) {

		return extractClaims(token).getSubject();
	}

	public Claims extractClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, String email) {

		return extractEmail(token).equals(email);
	}
}