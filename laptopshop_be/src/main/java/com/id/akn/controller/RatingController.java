package com.id.akn.controller;

import java.util.List;

import com.id.akn.exception.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.id.akn.exception.LaptopException;
import com.id.akn.exception.UserException;
import com.id.akn.model.User;
import com.id.akn.request.RatingDTO;
import com.id.akn.service.RatingService;
import com.id.akn.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/ratings")
@AllArgsConstructor
public class RatingController {
	
	private UserService userService;
	private RatingService ratingService;

	@PostMapping("/create")
	public ResponseEntity<com.id.akn.model.Rating> createRatingHandler(@RequestBody(required = false) RatingDTO req, @RequestHeader("Authorization") String jwt) throws UserException, LaptopException, OrderException {
		User user=userService.findUserProfileByJwt(jwt);
		com.id.akn.model.Rating rating=ratingService.createRating(req, user);
		return new ResponseEntity<>(rating,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/laptop/{laptopId}")
	public ResponseEntity<List<com.id.akn.model.Rating>> getLaptopRatingsHandler(@PathVariable Long laptopId){
		List<com.id.akn.model.Rating> ratings = ratingService.getLaptopRatings(laptopId);
		return new ResponseEntity<>(ratings,HttpStatus.OK);
	}
}