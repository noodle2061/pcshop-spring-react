package com.id.akn.service;

import com.id.akn.exception.UserException;
import com.id.akn.model.User;
import com.id.akn.request.UserSignupDTO;

import java.util.List;

public interface UserService {
	User createUser(UserSignupDTO userSignupDTO) throws UserException;
	User findUserById(Long userId) throws UserException;
	User findUserProfileByJwt(String jwt) throws UserException;
	List<User> findAllUsers();
}
