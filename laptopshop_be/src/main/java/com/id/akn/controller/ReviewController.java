package com.id.akn.controller;

import java.io.IOException;
import java.util.List;
import java.util.Collections;


import com.id.akn.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.id.akn.model.User;
import com.id.akn.request.ReviewDTO;
import com.id.akn.service.ReviewService;
import com.id.akn.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
	
	private ReviewService reviewService;
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<?> createReviewHandler(@RequestBody(required = false) ReviewDTO req, @RequestHeader("Authorization") String jwt) throws UserException, LaptopException, OrderException {
		try {
//			if (req.getOrderId() == null) {
//				return new ResponseEntity<>(
//						Collections.singletonMap("message", "You have not placed an order yet"),
//						HttpStatus.NOT_FOUND
//				);
//			}

			User user = userService.findUserProfileByJwt(jwt);
			com.id.akn.model.Review review = reviewService.createReview(req, user);
			return new ResponseEntity<>(review, HttpStatus.CREATED);
		} catch (OrderException e) {
			return new ResponseEntity<>(
					Collections.singletonMap("message", e.getMessage()),
					HttpStatus.NOT_FOUND
			);
		} catch (Exception e) {
			return new ResponseEntity<>(
					Collections.singletonMap("message", "Internal Server Error"),
					HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
	}


	@GetMapping("/laptop/{laptopId}")
	public ResponseEntity<List<com.id.akn.model.Review>> getLaptopReviewsHandler(@PathVariable Long laptopId){
		List<com.id.akn.model.Review>reviews=reviewService.getLaptopReviews(laptopId);
		return new ResponseEntity<List<com.id.akn.model.Review>>(reviews,HttpStatus.OK);
	}

}