package com.id.akn.controller;

import com.id.akn.request.UserSignupDTO;
import com.id.akn.service.UserService;
import com.id.akn.serviceimpl.CustomUserDetail;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.id.akn.config.JwtProvider;
import com.id.akn.exception.UserException;
import com.id.akn.model.Cart;
import com.id.akn.model.User;
import com.id.akn.request.Login;
import com.id.akn.response.AuthRes;
import com.id.akn.service.CartService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
	private UserService userService;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	private CustomUserDetail customUserDetail;
	private CartService cartService; 

	@PostMapping("/signup")	
	public ResponseEntity<AuthRes> createUserHandler(@Valid @RequestBody UserSignupDTO userSignupDTO) throws UserException {
		User savedUser =userService.createUser(userSignupDTO);
		Cart cart = cartService.createCart(savedUser);
		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		AuthRes authRes = new AuthRes();
		authRes.setJwt(token);
		authRes.setMessage("Đăng ký thành công");
		return new ResponseEntity<>(authRes, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthRes> loginUserHandler(@RequestBody Login login){
		Authentication authentication = authenticate(login.getEmail(), login.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		AuthRes authRes = new AuthRes();
		authRes.setJwt( jwtProvider.generateToken(authentication));
		authRes.setMessage("Đăng nhập thành công");
		return new ResponseEntity<>(authRes, HttpStatus.CREATED);
	}
	
	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customUserDetail.loadUserByUsername(username);
		if(userDetails==null) {
			throw new BadCredentialsException("Tên đăng nhập không hợp lệ");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Mật khẩu không hợp lệ");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
