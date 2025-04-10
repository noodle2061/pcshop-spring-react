package com.id.akn.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication auth) {
		String role = auth.getAuthorities().stream()
				.findFirst()
				.map(GrantedAuthority::getAuthority)
				.orElse("ROLE_USER"); // Lấy vai trò đầu tiên (mặc định ROLE_USER nếu không có)

		String jwt = Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 846000000))
				.claim("email", auth.getName())
				.claim("role", role)
				.signWith(key)
				.compact();
		return jwt;
	}


	public String getEmailFromToken(String jwt) {
		jwt = jwt.substring(7);
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		String email = String.valueOf(claims.get("email"));
		return email;
	}
}
