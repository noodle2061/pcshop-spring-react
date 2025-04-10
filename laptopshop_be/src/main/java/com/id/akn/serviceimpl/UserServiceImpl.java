package com.id.akn.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.id.akn.request.UserSignupDTO;
import com.id.akn.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.id.akn.config.JwtProvider;
import com.id.akn.exception.UserException;
import com.id.akn.model.User;
import com.id.akn.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserSignupDTO userSignupDTO) throws UserException {
		User isEmailExist = userRepository.findByEmail(userSignupDTO.getEmail());
		if(isEmailExist!=null) {
			throw new UserException("Email đã được sử dụng với một tài khoản khác");
		}
		return userRepository.save(convertToEntity(userSignupDTO));
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserException("Không tìm thấy người dùng có id: " + userId));
	}
	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		return Optional.ofNullable(userRepository.findByEmail(jwtProvider.getEmailFromToken(jwt)))
				.orElseThrow(() -> new UserException("Không tìm thấy người dùng có email: " + jwtProvider.getEmailFromToken(jwt)));
	}
	@Override
	public List<User> findAllUsers() {
		return userRepository.findAllByOrderByCreatedAtDesc();
	}
	public User convertToEntity(UserSignupDTO userSignupDTO) {
		User user = new User();
		user.setName(userSignupDTO.getName());
		user.setEmail(userSignupDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userSignupDTO.getPassword()));
		user.setPhoneNumber(userSignupDTO.getPhoneNumber());
		user.setGender(User.Gender.valueOf(userSignupDTO.getGender().toUpperCase()));
		user.setBirthday(userSignupDTO.getBirthday());
		user.setRole(User.Role.USER);
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
}
