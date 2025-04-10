package com.id.akn.service;

import java.util.List;

import com.id.akn.exception.LaptopException;
import com.id.akn.exception.OrderException;
import com.id.akn.model.User;
import com.id.akn.request.RatingDTO;


public interface RatingService {
	public com.id.akn.model.Rating createRating(RatingDTO req, User user) throws LaptopException, OrderException;
	public List<com.id.akn.model.Rating> getLaptopRatings(Long laptopId);
}
