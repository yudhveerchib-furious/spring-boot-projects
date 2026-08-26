package com.example.MovieBookingApp.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
     @Value("${jwt.secret}")
     private String secretKey;
     @Value("${jwt.expiration}")
     private Long jwtexpiration;

     public String extractUsername(String jwtToken) {
         return extractClaim(jwtToken, Claims::getSubject);
     }

     public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
         final Claims claims = extractAllClaims(jwtToken);
         return claimResolver.apply(claims);
     }
     private Claims extractAllClaims(String jwtToken) {
         return Jwts.parser()
                 .verifyWith(getSignInKey())
                 .build()
                 .parseSignedClaims(jwtToken)
                 .getPayload();
     }
     public SecretKey getSignInKey() {
         return Keys.hmacShaKeyFor(secretKey.getBytes());
     }

     public String generateToken(UserDetails userDetails) {
         return generateToken(new HashMap<>(), userDetails);
     }

     public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
         return Jwts.builder()
                 .claims(claims)
                 .subject(userDetails.getUsername())
                 .issuedAt(new Date(System.currentTimeMillis()))
                 .expiration(new Date(System.currentTimeMillis() + jwtexpiration ))
                 .signWith(getSignInKey())
                 .compact();
     }

     public boolean isValidToken(String jwtToken , UserDetails userDetails) {
         final String username = extractUsername(jwtToken);
         return (userDetails.getUsername().equals(username)) && !isTokenExpired(jwtToken);
     }

     private boolean isTokenExpired(String jwtToken) {
         return extractExpiration(jwtToken).before(new Date());
     }

     private Date extractExpiration(String jwtToken) {
         return extractClaim(jwtToken, Claims::getExpiration);
     }
}
