package com.id.akn.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		if ("Bearer null".equals(jwt)) {
			jwt = null;
		}
		if (jwt != null) {
			jwt = jwt.substring(7); // Loại bỏ tiền tố "Bearer "
			try {
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				Claims claims = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(jwt)
						.getBody();
				String email = String.valueOf(claims.get("email"));
				String role = String.valueOf(claims.get("role")); // Lấy vai trò từ JWT
				List<GrantedAuthority> auths = List.of(new SimpleGrantedAuthority(role)); // Chuyển thành danh sách quyền
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				throw new BadCredentialsException("Invalid token from jwt validator");
			}
		}
		filterChain.doFilter(request, response);
	}
}
