package com.id.akn.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.id.akn.model.User;
import com.id.akn.repository.UserRepository;

@Service
@AllArgsConstructor
public class CustomUserDetail implements UserDetailsService{
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("User not found with email: "+ username);
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		if (user.getRole() != null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}
}
